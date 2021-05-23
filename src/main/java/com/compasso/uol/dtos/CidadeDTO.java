package com.compasso.uol.dtos;

import com.compasso.uol.enums.EstadoEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CidadeDTO {

    @NotBlank(message = "Cidade vazia")
    @Size(min = 3, max = 100, message = "Cidada excedeu o limite de caracteres")
    private String nome;
    @NotNull(message = "Estado vazio")
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
