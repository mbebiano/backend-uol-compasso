package com.compasso.uol.services;

import com.compasso.uol.dtos.ClienteDTO;
import com.compasso.uol.models.Cliente;

import javax.validation.Valid;
import java.util.List;

public interface ClienteService {

    /**
     * Método responsável por cadastrar um novo cliente
     *
     * @param clienteDTO DTO contendo atributos necessários para criar um novo objeto Cliente
     * @return Cliente salvo
     */
    Cliente cadastrarCliente(@Valid ClienteDTO clienteDTO);

    /**
     * Método responsável por buscar um cliente por id
     *
     * @param id atributo responsável por identificar um cliente unicamente
     * @return Cliente, caso encontrado
     */
    Cliente consultarClientePorId(long id);

    /**
     * Método responsável por buscar clientes com o nome definido para o atributo
     *
     * @param nome atributo responsável por identificar um cliente
     * @return Cliente(s), caso encontrado
     */
    List<Cliente> consultarClientePorNome(String nome);

    /**
     * Método responsável por deletar cliente, caso exista
     *
     * @param id atributo responsável por identificar um cliente unicamente
     */
    void deletarCliente(long id);

    /**
     * Método responsável por alterar nome do cliente
     *
     * @param id   atributo responsável por identificar um cliente unicamente
     * @param nome atributo responsável por identificar um cliente
     */
    Cliente alterarNomeCliente(long id, String nome);

}
