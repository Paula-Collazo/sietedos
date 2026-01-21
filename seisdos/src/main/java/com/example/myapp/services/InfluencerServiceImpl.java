package com.example.myapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.Repository.InfluencerRepository;
import com.example.myapp.domain.Influencer;

@Service
public class InfluencerServiceImpl implements InfluencerService {

    @Autowired
    private InfluencerRepository repositorioInfluencer;

    @Override
    public Influencer añadir(Influencer influencer) {
        return repositorioInfluencer.save(influencer);    }

    @Override
    public List<Influencer> obtenerTodos() {
        return repositorioInfluencer.findAll();
    }

    @Override
    public Influencer obtenerPorId(long id) {
        return repositorioInfluencer.findById(id).orElse(null);
    }

    @Override
    public Influencer editar(Influencer influencer) {
        return repositorioInfluencer.save(influencer);
    }

    @Override
    public void borrar(Long id) {
       repositorioInfluencer.deleteById(id);
    }

    
}
