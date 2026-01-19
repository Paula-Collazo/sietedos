package com.example.myapp.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.Repository.CursoRepository;
import com.example.myapp.Repository.VideoRepository;
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
    public Set<Video> obtenerPorCursoId(long id) {

    // 1️⃣ Buscamos en la base de datos un Curso con el id recibido
    //    findById devuelve un Optional<Curso>, porque el curso puede existir o no
    return cursoRepository.findById(id)

            // 2️⃣ Si el curso EXISTE dentro del Optional:
            //    - se ejecuta el map
            //    - el Curso se pasa como parámetro a findByCurso
            //    - findByCurso devuelve un Set<Video> con todos los vídeos de ese curso
            //    El resultado pasa a ser Optional<Set<Video>>
            .map(repositorioVideo::findByCurso)

            // 3️⃣ Si el curso NO existe (Optional vacío):
            //    - el map NO se ejecuta
            //    - orElse devuelve el valor por defecto
            //    En este caso, un Set vacío (sin vídeos)
            .orElse(Set.of());
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
