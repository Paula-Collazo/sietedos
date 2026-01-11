package com.example.myapp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myapp.domain.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{
    List<Curso> findByPrecioLessThan(Double precio);
//     @Query("select c from Curso c " +
//         "where c.precio < (select c2.precio from Curso c2)")
//     List<Curso> queryByCursoLessThanOtherCurso();
}
