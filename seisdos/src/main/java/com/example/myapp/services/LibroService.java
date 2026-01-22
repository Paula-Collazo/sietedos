package com.example.myapp.services;

import java.util.List;
import com.example.myapp.domain.Libro;
import com.example.myapp.domain.Curso;



public interface LibroService {
    Libro anadir(Libro curso);

    List<Libro> obtenerTodos();

    Libro obtenerPorId(long id) ;

    Libro editar(Libro curso) ;

    void borrar(Long id);

    Libro obtenerPorCurso(Curso curso);

}
