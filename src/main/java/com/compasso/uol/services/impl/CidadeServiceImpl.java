package com.compasso.uol.services.impl;

import com.compasso.uol.dtos.CidadeDTO;
import com.compasso.uol.enums.EstadoEnum;
import com.compasso.uol.exceptions.BusinessException;
import com.compasso.uol.exceptions.ResourceNotFoundException;
import com.compasso.uol.models.Cidade;
import com.compasso.uol.repositories.CidadeRepository;
import com.compasso.uol.services.CidadeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class CidadeServiceImpl implements CidadeService {

    private final CidadeRepository cidadeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CidadeServiceImpl(CidadeRepository cidadeRepository, ModelMapper modelMapper) {
        this.cidadeRepository = cidadeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Cidade consultarCidadePorId(long id) {
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cidade com id " + id + " não encontrado"));
    }

    @Override
    public Cidade cadastrarCidade(@Valid CidadeDTO cidadeDTO) {
        Cidade cidade = modelMapper.map(cidadeDTO, Cidade.class);
        List<Cidade> cidadeRetornoList = cidadeRepository.findAllByNomeIgnoreCase(cidade.getNome());
        if (!cidadeRetornoList.isEmpty()) {
            cidadeRetornoList.forEach(cidadeRetorno -> {
                if (cidadeRetorno.getEstado().equals(cidade.getEstado())) {
                    throw new BusinessException("Já existe uma cidade com esse nome para o estado " + cidade.getEstado());
                }
            });
        }
        return cidadeRepository.save(cidade);
    }

    @Override
    public List<Cidade> buscarCidades(EstadoEnum estadoEnum, String nome) {
        validaParametros(nome, estadoEnum);
        List<Cidade> cidades = Collections.emptyList();
        if (estadoEnum != null) {
            cidades = consultarCidadePorEstado(estadoEnum);
        }
        if (nome != null) {
            cidades = consultarCidadesPorNome(nome);
        }
        return cidades;
    }


    private List<Cidade> consultarCidadesPorNome(String nome) {
        List<Cidade> cidades = cidadeRepository.findAllByNomeIgnoreCase(nome);
        if (cidades.isEmpty()) {
            return Collections.emptyList();
        }
        return cidades;
    }

    private List<Cidade> consultarCidadePorEstado(EstadoEnum estadoEnum) {
        List<Cidade> cidades = cidadeRepository.findAllByEstado(estadoEnum);
        if (cidades.isEmpty()) {
            throw new ResourceNotFoundException("Não foram encontradas cidades para esse estado" + estadoEnum.getNome());
        }
        return cidades;
    }

    private void validaParametros(String nome, EstadoEnum estadoEnum) {
        if (nome != null && estadoEnum != null) {
            throw new BusinessException("Preencha apenas um dos campos, nome ou estado");
        }
        if (nome == null && estadoEnum == null) {
            throw new BusinessException("Nenhum dos campos foi preenchido");
        }
    }
}