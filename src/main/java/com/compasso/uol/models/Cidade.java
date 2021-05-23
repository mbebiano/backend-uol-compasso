package com.compasso.uol.models;

import com.compasso.uol.enums.EstadoEnum;

import javax.persistence.*;

@Entity
@Table(name = "cidade")
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cidade_id", nullable = false)
    private long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoEnum estado;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EstadoEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }
}
