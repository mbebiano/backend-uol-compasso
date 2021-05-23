package com.compasso.uol.resources;

import com.compasso.uol.dtos.ClienteRetornoDTO;
import com.compasso.uol.models.Cliente;
import com.compasso.uol.services.impl.ClienteServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteLeitura {

    private final ClienteServiceImpl clienteService;
    private final ModelMapper modelMapper;

    @Autowired
    public ClienteLeitura(ClienteServiceImpl clienteService, ModelMapper modelMapper) {
        this.clienteService = clienteService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteRetornoDTO> buscarClientePorId(@PathVariable long id) {
        Cliente cliente = clienteService.consultarClientePorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(cliente, ClienteRetornoDTO.class));
    }

    @GetMapping(value = "/buscar")
    public ResponseEntity<ClienteRetornoDTO[]> buscarClientePorNome(@RequestParam String nome) {
        List<Cliente> clientes = clienteService.consultarClientePorNome(nome);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(clientes, ClienteRetornoDTO[].class));
    }
}