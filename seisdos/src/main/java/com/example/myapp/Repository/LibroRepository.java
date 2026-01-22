package com.example.myapp.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myapp.domain.Curso;
import com.example.myapp.domain.Libro;


public interface LibroRepository extends JpaRepository<Libro, Long> {
  Libro findByCurso(Curso curso);
}
