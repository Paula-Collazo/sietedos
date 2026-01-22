package com.example.myapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

@Entity
public class Libro {

    @Id
    @GeneratedValue
    @Min(value = 0)
    private Long id;

    @NotEmpty
    private String titulo;

    @OneToOne
    private Curso curso;
    
}
