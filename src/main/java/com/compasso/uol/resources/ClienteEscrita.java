package com.compasso.uol.resources;

import com.compasso.uol.dtos.ClienteAlteraDTO;
import com.compasso.uol.dtos.ClienteDTO;
import com.compasso.uol.dtos.ClienteRetornoDTO;
import com.compasso.uol.models.Cliente;
import com.compasso.uol.services.impl.ClienteServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteEscrita {

    private final ClienteServiceImpl clienteService;
    private final ModelMapper modelMapper;

    @Autowired
    public ClienteEscrita(ClienteServiceImpl clienteService, ModelMapper modelMapper) {
        this.clienteService = clienteService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<ClienteRetornoDTO> criarCliente(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.cadastrarCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(cliente, ClienteRetornoDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteRetornoDTO> alterarCliente(@RequestBody ClienteAlteraDTO clienteAlteraDTO, @PathVariable long id) {
        Cliente cliente = clienteService.alterarNomeCliente(id, clienteAlteraDTO.getNomeCompleto());
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(cliente, ClienteRetornoDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}