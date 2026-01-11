package com.example.myapp.services;

import java.util.List;

import com.example.myapp.domain.Curso;


public interface CursoService {
    Curso añadir(Curso curso);

    List<Curso> obtenerTodos();

    Curso obtenerPorId(long id) ;

    Curso editar(Curso curso) ;

    void borrar(Long id);

    List<Curso> buscarPorNombre(String nombre);

    List<Curso> buscarPorNombreYTematica(String nombre, com.example.myapp.domain.Tematica tematica);

    List<Curso> obtenerCursoPrecioMenor (double precio);

    

}
