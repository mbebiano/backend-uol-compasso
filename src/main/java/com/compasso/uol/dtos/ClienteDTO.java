package com.compasso.uol.dtos;

import com.compasso.uol.enums.SexoEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class ClienteDTO {
    @Size(min = 3, max = 100, message = "Nome completo tamanho inválido")
    @NotBlank(message = "Obrigatório preenchimento nome")
    private String nomeCompleto;

    @NotNull(message = "Sexo nulo")
    private SexoEnum sexoEnum;

    @NotNull(message = "Data de nascimento nula")
    @DateTimeFormat(pattern = "dd.MM.yy")
    @Temporal(value= TemporalType.TIMESTAMP)
    private LocalDate dataNascimento;

    private long idCidade;

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public SexoEnum getSexoEnum() {
        return sexoEnum;
    }

    public void setSexoEnum(SexoEnum sexoEnum) {
        this.sexoEnum = sexoEnum;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public long getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(long idCidade) {
        this.idCidade = idCidade;
    }
}

