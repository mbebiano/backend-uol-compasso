package com.compasso.uol.services.impl;

import com.compasso.uol.dtos.ClienteDTO;
import com.compasso.uol.exceptions.BusinessException;
import com.compasso.uol.exceptions.ResourceNotFoundException;
import com.compasso.uol.models.Cliente;
import com.compasso.uol.repositories.ClienteRepository;
import com.compasso.uol.services.CidadeService;
import com.compasso.uol.services.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

@Service
@Validated
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    private final CidadeService cidadeService;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository, ModelMapper modelMapper, CidadeService cidadeService) {
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
        this.cidadeService = cidadeService;
    }

    @Override
    public Cliente cadastrarCliente(@Valid ClienteDTO clienteDTO) {
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        cliente.setIdade(Math.toIntExact(ChronoUnit.YEARS.between(clienteDTO.getDataNascimento(), LocalDate.now())));
        cidadeService.consultarCidadePorId(clienteDTO.getIdCidade());
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente consultarClientePorId(long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente com id " + id + " não encontrado"));
    }

    @Override
    public List<Cliente> consultarClientePorNome(String nome) {
        List<Cliente> clientes = clienteRepository.findAllByNomeCompletoIgnoreCase(nome);
        if (clientes.isEmpty()) {
            return Collections.emptyList();
        }
        return clientes;
    }

    @Override
    public void deletarCliente(long id) {
        Cliente cliente = consultarClientePorId(id);
        clienteRepository.delete(cliente);
    }

    @Override
    public Cliente alterarNomeCliente(long id, String nome) {
        Cliente cliente = consultarClientePorId(id);
        if (cliente.getNomeCompleto().equals(nome)) {
            throw new BusinessException("Não foi póssivel fazer essa alteração, nome anterior igual ao nome atual");
        } else {
            cliente.setNomeCompleto(nome);
            clienteRepository.save(cliente);
        }
        return cliente;
    }
}