package com.example.myapp;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.myapp.domain.Autor;
import com.example.myapp.domain.Curso;
import com.example.myapp.domain.Tematica;
import com.example.myapp.domain.Video;
import com.example.myapp.services.AutorService;
import com.example.myapp.services.CursoService;
import com.example.myapp.services.VideoService;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	CommandLineRunner initData(CursoService cursoService, AutorService autorService, VideoService videoService ) {
		return args -> {
			Curso curso = new Curso();
			curso.setNombre("Curso de Programación desde cero");
			curso.setPrecio(1000D);
			curso.setTematica(Tematica.PROGRAMACION);
			// cursoService.añadir(new Curso("Curso de Programación desde cero", 1000D, Tematica.PROGRAMACION));
			// cursoService.añadir(new Curso("Aprende Java Profesional", 1500D, Tematica.PROGRAMACION));

			Autor autor = new Autor();
			autor.setNombre("Larry Capaja");
			autor.setEmail("larryCa@gmail.com");
			autor.setLimiteCosteTotalCursos(5000.0);

			Video video = new Video();
			video.setDescripcion("PRUEBAAAAAAA");
			video.setDuracion(500);
			video.setIdYt("jdlfajdsljfv");
			curso.getVideos().add(video);
			video.setCurso(curso);
				
			
			curso.setAutor(autor);
			autorService.añadir(autor);
			cursoService.añadir(curso);

			Curso curso2 = new Curso();
			curso2.setNombre("Aprende Java Profesional");
			curso2.setPrecio(1500D);
			curso2.setTematica(Tematica.PROGRAMACION);

			Autor autor2 = new Autor();
			autor2.setNombre("Ana Bohuelo");
			autor2.setEmail("AnitaBo@gmail.com");
			autor2.setLimiteCosteTotalCursos(3000.0);

			curso2.setAutor(autor2);
			autorService.añadir(autor2);
			cursoService.añadir(curso2);


		};
	}

}
