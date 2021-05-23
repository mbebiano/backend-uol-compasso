package com.compasso.uol.resources;

import com.compasso.uol.dtos.CidadeDTO;
import com.compasso.uol.dtos.CidadeRetornoDTO;
import com.compasso.uol.models.Cidade;
import com.compasso.uol.services.CidadeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeEscrita {

    private final CidadeService cidadeService;
    private final ModelMapper modelMapper;

    @Autowired
    public CidadeEscrita(CidadeService cidadeService, ModelMapper modelMapper) {
        this.cidadeService = cidadeService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<CidadeRetornoDTO> criarCidade(@RequestBody CidadeDTO cidadeDTO) {
        Cidade cidade = cidadeService.cadastrarCidade(cidadeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(cidade, CidadeRetornoDTO.class));
    }
}