package com.example.myapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.Repository.VideoRepository;
import com.example.myapp.Repository.CursoRepository;
import com.example.myapp.domain.Video;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository repositorioVideo;
    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public Video añadir(Video video) {
        return repositorioVideo.save(video);
    }

    @Override
    public List<Video> obtenerTodos() {
       return repositorioVideo.findAll();
    }

    @Override
    public Video obtenerPorCursoId(long id) {
        // return repositorioVideo.findByCurso(cursoRepository.findById(id)).orElse(null);

        // return repositorioVideo.findByCurso(cursoRepository.findById(id)).orElse(null);
    }
    @Override
    public Video obtenerPorId(long id) {
        return repositorioVideo.findById(id).orElse(null);
    }

    @Override
    public Video editar(Video video) {
       return repositorioVideo.save(video);
    }

    @Override
    public void borrar(Long id) {
        repositorioVideo.deleteById(id);
    }
    
}
