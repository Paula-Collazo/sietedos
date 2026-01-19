package com.example.myapp.services;

import java.util.List;
import java.util.Set;

import com.example.myapp.domain.Video;


public interface VideoService {
    Video añadir(Video video);
    List<Video> obtenerTodos();
    Video obtenerPorId(long id);
    Set<Video> obtenerPorCursoId(long id);
    Video editar (Video video);
    void borrar(Long id);
}
