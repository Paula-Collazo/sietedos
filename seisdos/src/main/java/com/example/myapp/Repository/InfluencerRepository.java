package com.example.myapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myapp.domain.Influencer;

public interface InfluencerRepository extends JpaRepository<Influencer, Long> {
    
}
