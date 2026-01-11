package com.example.myapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.Repository.AutorRepository;
import com.example.myapp.domain.Autor;

@Service
public class AutorServiceImpl implements AutorService {
    @Autowired
    private AutorRepository autorRepository;

    @Override
    public Autor añadir(Autor autor) {
        return autorRepository.save(autor);
    }

    @Override
    public List<Autor> obtenerTodos() {
        return autorRepository.findAll();
    }

    @Override
    public Autor obtenerPorId(long id) {
         return autorRepository.findById(id).orElse(null);
    }

    @Override
    public Autor editar(Autor autor) {
         return autorRepository.save(autor);
    }

    @Override
    public void borrar(Long id) {
        autorRepository.deleteById(id);
    }

    @Override
    public Autor obtenerPorNombre(String nombre) {
         return autorRepository.findByNombre(nombre);
    }
    
}
