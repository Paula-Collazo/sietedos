package com.example.myapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myapp.domain.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long>{
    Autor findByNombre(String nombre);
}
