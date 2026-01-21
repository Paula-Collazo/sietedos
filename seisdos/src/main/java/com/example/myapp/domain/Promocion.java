package com.example.myapp.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Promocion {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Double remuneracion;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    @OnDelete(action=OnDeleteAction.CASCADE)
    private Curso curso;
    @ManyToOne
    @JoinColumn(name = "influencer_id")
    @OnDelete(action=OnDeleteAction.CASCADE)
    private Influencer influencer;

    
}
