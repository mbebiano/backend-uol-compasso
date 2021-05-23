package com.compasso.uol.services;

import com.compasso.uol.dtos.CidadeDTO;
import com.compasso.uol.enums.EstadoEnum;
import com.compasso.uol.models.Cidade;

import javax.validation.Valid;
import java.util.List;

public interface CidadeService {

    /**
     * Método responsável por buscar uma cidade por id
     *
     * @param id atributo responsável por identificar uma cidade unicamente
     * @return Cidade, caso encontrado
     */
    Cidade consultarCidadePorId(long id);

    /**
     * Método responsável por cadastrar uma nova cidade
     *
     * @param cidadeDTO DTO contendo atributos necessários para criar um novo objeto Cidade
     * @return Cidade salva
     */
    Cidade cadastrarCidade(@Valid CidadeDTO cidadeDTO);

    /**
     * Método responsável por buscar cidade por nome ou buscar cidades cadastradas no mesmo estado
     *
     * @param nome atributo responsável por identificar uma cidade
     * @return Cidade(s) encontradas
     */
    List<Cidade> buscarCidades(EstadoEnum estadoEnum, String nome);
}
