package com.compasso.uol.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ClienteAlteraDTO {
    @NotBlank(message = "Nome completo tamanho inválido")
    @Size(min = 3, message = "Obrigatório preenchimento nome")
    private String nomeCompleto;

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }
}
