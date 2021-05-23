package com.compasso.uol.enums;

public enum SexoEnum {

    FEMININO("Feminino"),
    INDEFINIDO("Indefinido"),
    MASCULINO("Masculino");

    private final String sexo;

    SexoEnum(String sexo) {
        this.sexo = sexo;
    }

    public String getSexo() {
        return sexo;
    }
}