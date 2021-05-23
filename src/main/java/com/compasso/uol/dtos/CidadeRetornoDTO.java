package com.compasso.uol.dtos;

import com.compasso.uol.enums.EstadoEnum;

public class CidadeRetornoDTO {

    private String nome;
    private EstadoEnum estado;

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
