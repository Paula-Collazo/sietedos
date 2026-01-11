package com.example.myapp.services;

import java.util.List;

import com.example.myapp.domain.Autor;

public interface  AutorService {
    Autor añadir(Autor autor);
    List<Autor> obtenerTodos();
    Autor obtenerPorId(long id);
    Autor editar (Autor autor);
    void borrar(Long id);
    Autor obtenerPorNombre(String nombre);


}
