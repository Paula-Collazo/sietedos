package com.example.myapp.services;

import java.util.List;

import com.example.myapp.domain.Influencer;

public interface InfluencerService {
    Influencer añadir(Influencer influencer);
    List<Influencer> obtenerTodos();
    Influencer obtenerPorId(long id);
    Influencer editar (Influencer influencer);
    void borrar(Long id);
    
}
