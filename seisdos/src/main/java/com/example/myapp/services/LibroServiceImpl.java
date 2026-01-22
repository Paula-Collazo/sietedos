package com.example.myapp.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.Repository.LibroRepository;
import com.example.myapp.domain.Curso;
import com.example.myapp.domain.Libro;
import com.example.myapp.domain.Video;

@Service
public class LibroServiceImpl implements LibroService {
    @Autowired
    private LibroRepository repositorio;

    @Override
    public Libro anadir(Libro curso) {
        return repositorio.save(curso);
    }
    
    @Override
    public List<Libro> obtenerTodos() {
        return repositorio.findAll();
    }
    
    @Override
    public Libro obtenerPorId(long id) {
        return repositorio.findById(id).orElse(null);
    }
    
    @Override
    public Libro editar(Libro curso) {
        return repositorio.save(curso);
    }
    
    @Override
    public void borrar(Long id) {
        repositorio.deleteById(id);
}

    @Override
    public Libro obtenerPorCurso(Curso curso) {
        return repositorio.findByCurso(curso);

}
}

   

