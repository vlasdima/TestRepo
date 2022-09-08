package com.example.demo.datasource;

import com.example.demo.model.Person;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class PostgresDatasourceHibernate {

    private Configuration conn;
    private SessionFactory sf;
    private Session session;

    private EntityManagerFactory emf;

    public PostgresDatasourceHibernate(){
        conn = new Configuration().configure();
        conn.addAnnotatedClass(Person.class);
        sf = conn.buildSessionFactory();
        session = sf.openSession();
        emf = session.getEntityManagerFactory();

    }

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }


}
