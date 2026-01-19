package com.example.myapp.Repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myapp.domain.Curso;
import com.example.myapp.domain.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {
    Set<Video> findByCurso(Curso curso);
}
