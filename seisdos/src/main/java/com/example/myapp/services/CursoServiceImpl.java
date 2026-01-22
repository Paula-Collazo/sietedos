package com.example.myapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.myapp.Repository.CursoRepository;
import com.example.myapp.Repository.LibroRepository;
import com.example.myapp.domain.Curso;

@Service
@Primary
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository repositorio;

    @Autowired
    private LibroRepository repositorioLibro;



    @Override
    public List<Curso> obtenerTodos() {
        return repositorio.findAll();
    }

    @Override
    public Curso obtenerPorId(long id) throws RuntimeException {
        return repositorio.findById(id).orElseThrow(
            ()-> new RuntimeException("Curso no encontrado"));
        
        // for (Curso curso : repositorio)
        //     if (curso.getId() == id)
        //         return curso;
        // throw new RuntimeException("Curso no encontrado");
    }

    @Override
    public Curso añadir(Curso curso) throws RuntimeException{
       
         if(curso.getPrecio() > 5000){
             throw new RuntimeException("El precio no puede superar los 5000");
         }

         Double totalActual = repositorio.sumaTotalCursosPorAutor(curso.getAutor().getId());
         
         if (totalActual == null) {
             totalActual = 0.0;
         }
         
         Double nuevoTotal = totalActual + curso.getPrecio();

         if (curso.getAutor().getLimiteCosteTotalCursos() != null &&
             nuevoTotal > curso.getAutor().getLimiteCosteTotalCursos()) {
            throw new RuntimeException("El coste total de los cursos supera el límite permitido para este autor/a");
            
         }
          return repositorio.save(curso);
        
        // if (repositorio.contains(curso))
        //     return null;
        // // ver equals Curso (mismo id)
        // repositorio.add(curso);
        // return curso; // podría no devolver nada, o boolean, etc

    }

    @Override
    public Curso editar(Curso curso) throws RuntimeException{

        if(curso.getPrecio() > 5000){
            throw new RuntimeException("El precio no puede superar los 5000");
        }

        obtenerPorId(curso.getId());
        
        Curso cursoAnterior = repositorio.findById(curso.getId()).get();
        
        Double total = repositorio.sumaTotalCursosPorAutor(curso.getAutor().getId());

        if (total == null) {
            total = 0.0;
        }
        
        // Restamos el precio anterior y sumamos el nuevo
        Double nuevoTotal = total - cursoAnterior.getPrecio() + curso.getPrecio();

        if (curso.getAutor().getLimiteCosteTotalCursos() != null &&
            nuevoTotal > curso.getAutor().getLimiteCosteTotalCursos()) {
            throw new RuntimeException(
                "El coste total de los cursos supera el límite permitido para este autor/a"
            );
        }
        return repositorio.save(curso);
        // int pos = repositorio.indexOf(curso);
        // // if (pos == -1) throw new RuntimeException ("Curso no encontrado");
        // if (pos == -1)
        //     throw new RuntimeException("Curso no encontrado");
        // repositorio.set(pos, curso);
        // return curso;
    }

    @Override
    public void borrar(Long id) {
        obtenerPorId(id);
        
        Curso curso = repositorio.findById(id).orElseThrow(() -> new IllegalStateException("Curso no encontrado"));

        if (repositorioLibro.findByCurso(curso) != null ) {
            throw new IllegalStateException("No se puede borrar el curso: tiene un libro asignado");
        }//lanza excepción si no existe
        repositorio.deleteById(id);
        // Curso curso = this.obtenerPorId(id);
        // if (curso != null)
        //     repositorio.remove(curso);
    }
    
    @Override
    public List<Curso> buscarPorNombre(String nombre){
        List<Curso> resultado = new ArrayList<>();
        String nombreBusqueda = nombre.toLowerCase();
        for (Curso curso : repositorio.findAll()) {
            if (curso.getNombre().toLowerCase().contains(nombreBusqueda)) {
                resultado.add(curso);
            }

        }
        return resultado;
    }

    @Override
    public List<Curso> buscarPorNombreYTematica(String nombre, com.example.myapp.domain.Tematica tematica){
        List<Curso> resultado = new ArrayList<>();
        String nombreBusqueda = (nombre == null) ? "" : nombre.toLowerCase().trim();
        for (Curso curso : repositorio.findAll()) {
            boolean nameMatches = nombreBusqueda.isEmpty() || (curso.getNombre() != null && curso.getNombre().toLowerCase().contains(nombreBusqueda));
            boolean tematicaMatches = (tematica == null) || (curso.getTematica() == tematica);
            if (nameMatches && tematicaMatches) {
                resultado.add(curso);
            }
        }
        return resultado;
    }

    @Override
    public List<Curso> obtenerCursoPrecioMenor(double precio){
        return repositorio.findByPrecioLessThan(precio);
    }
}
