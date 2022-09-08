package com.example.demo.dao;

import com.example.demo.datasource.PostgresDatasourceHibernate;
import com.example.demo.model.Person;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres_hibernate")
public class PersonDataAccessServiceHibernate implements PersonDao {


    private PostgresDatasourceHibernate db = new PostgresDatasourceHibernate();
    private EntityManager em;

    @Override
    public int insertPerson(UUID id, Person person) {
        Person personToAdd = new Person(id,person.getName());
        em =db.getEntityManager();
        em.getTransaction().begin();
        em.persist(personToAdd);
        em.getTransaction().commit();
        em.close();
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {

        final String sql ="select person from Person person";
        em = db.getEntityManager();
        List people = em.createQuery(sql).getResultList();
        em.close();
        return people;

/*
        CriteriaQuery<Person> cq =
                em.getCriteriaBuilder()
                .createQuery(Person.class);
        cq.select(cq.from(Person.class));
        return em.createQuery(cq).getResultList();
*/
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        em = db.getEntityManager();
        em.getTransaction().begin();
        Person person = em.find(Person.class,id);
        em.getTransaction().commit();
        em.close();
        return Optional.ofNullable(person);
    }

    @Override
    public int deletePersonById(UUID id) {
 /*       try {
            Person personToDelete = selectPersonById(id).orElse(null);
            em.getTransaction().begin();
            em.detach(personToDelete);
            em.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Error in Delete");
            return -1;
        }
        return 1;*/


        final String sql="delete from Person where id=?1";
        em = db.getEntityManager();
        em.getTransaction().begin();
        em.createQuery(sql).setParameter(1,id).executeUpdate();
        em.getTransaction().commit();
        em.close();
        return 0;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        try {
            em = db.getEntityManager();
            EntityTransaction t = em.getTransaction();
            t.begin();
            Person personToUpdate = selectPersonById(id).orElse(null);
            personToUpdate.setName(person.getName());
            t.commit();
            em.close();
        }catch (Exception e){

            System.out.println("Error in Update");
            e.printStackTrace();
            return -1;
        } finally {
            em.close();
        }
        return 1;
    }

}
