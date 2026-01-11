package com.example.myapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.myapp.domain.Curso;
import com.example.myapp.domain.Tematica;
import com.example.myapp.services.CursoService;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	CommandLineRunner initData(CursoService cursoService) {
		return args -> {
			cursoService.añadir(new Curso("Curso de Programación desde cero", 1000D, Tematica.PROGRAMACION));
			cursoService.añadir(new Curso("Aprende Java Profesional", 1500D, Tematica.PROGRAMACION));

		};
	}

}
