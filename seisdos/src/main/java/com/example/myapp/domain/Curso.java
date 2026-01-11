package com.example.myapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class Curso {

    public Curso(String nombre, Double precio, Tematica tematica) {
    this.nombre = nombre;
    this.precio = precio;
    this.tematica = tematica;
}

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Autor autor;

    @NotEmpty
    private String nombre;

    private Double precio;

    private Tematica tematica;
}
