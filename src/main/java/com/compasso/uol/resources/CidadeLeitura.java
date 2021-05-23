package com.compasso.uol.resources;

import com.compasso.uol.dtos.CidadeRetornoDTO;
import com.compasso.uol.enums.EstadoEnum;
import com.compasso.uol.models.Cidade;
import com.compasso.uol.services.CidadeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeLeitura {

    private final CidadeService cidadeService;
    private final ModelMapper modelMapper;

    @Autowired
    public CidadeLeitura(CidadeService cidadeService, ModelMapper modelMapper) {
        this.cidadeService = cidadeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/buscar")
    public ResponseEntity<CidadeRetornoDTO[]> buscarCidadesPorEstado(@RequestParam(required = false) EstadoEnum estado, @RequestParam(required = false) String nome) {
        List<Cidade> cidades = cidadeService.buscarCidades(estado, nome);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(cidades, CidadeRetornoDTO[].class));
    }
}