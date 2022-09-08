package com.example.demo;

import com.example.demo.datasource.PostgresDatasource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		PostgresDatasource postgresDatasource = new PostgresDatasource();


		SpringApplication.run(DemoApplication.class, args);


	}



}
