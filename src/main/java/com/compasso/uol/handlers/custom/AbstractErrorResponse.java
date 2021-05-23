package com.compasso.uol.handlers.custom;

import java.time.LocalDateTime;

public class AbstractErrorResponse {
    private String mensagem;
    private int codigo;
    private String status;
    private LocalDateTime data;

    protected AbstractErrorResponse() {
        this.data = LocalDateTime.now();
    }

    protected AbstractErrorResponse(String mensagem, int codigo, String status) {
        this.mensagem = mensagem;
        this.codigo = codigo;
        this.status = status;
        this.data = LocalDateTime.now();
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
