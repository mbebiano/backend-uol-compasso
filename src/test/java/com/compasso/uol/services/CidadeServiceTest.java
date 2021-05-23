package com.compasso.uol.services;

import com.compasso.uol.config.ModelMapperConfig;
import com.compasso.uol.dtos.CidadeDTO;
import com.compasso.uol.enums.EstadoEnum;
import com.compasso.uol.exceptions.BusinessException;
import com.compasso.uol.exceptions.ResourceNotFoundException;
import com.compasso.uol.models.Cidade;
import com.compasso.uol.repositories.CidadeRepository;
import com.compasso.uol.services.impl.CidadeServiceImpl;
import com.compasso.uol.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CidadeServiceTest {

    private static final String MOCK_FOLDER = "mocks";
    private static final String MOCK_CIDADE = "Cidade.json";
    private static final String MOCK_CIDADE_DTO = "CidadeDTO.json";

    @Mock
    private CidadeRepository cidadeRepository;

    private final ModelMapper mapper = new ModelMapperConfig().modelMapper();

    private CidadeServiceImpl cidadeService() {
        return new CidadeServiceImpl(cidadeRepository, mapper);
    }

    static Cidade getMockCidade() {
        return TestUtils.getMock(MOCK_FOLDER, MOCK_CIDADE, Cidade.class);
    }

    static CidadeDTO getMockCidadeDTO() {
        return TestUtils.getMock(MOCK_FOLDER, MOCK_CIDADE_DTO, CidadeDTO.class);
    }

    @Test
    void testarSalvarCidade() {
        Cidade cidade = getMockCidade();
        when(cidadeRepository.save(Mockito.any())).thenReturn(cidade);
        Cidade cidadeSalva = cidadeService().cadastrarCidade(getMockCidadeDTO());
        assertEquals(cidadeSalva.getId(), cidade.getId());
        assertEquals(cidadeSalva.getNome(), cidade.getNome());
        assertEquals(cidadeSalva.getEstado(), cidade.getEstado());
    }

    @Test
    void testarSalvarCidadeJaExistente() {
        Cidade cidade = getMockCidade();
        when(cidadeRepository.save(Mockito.any())).thenReturn(cidade);
        when(cidadeRepository.findAllByNomeIgnoreCase(Mockito.anyString())).thenReturn(Collections.singletonList(cidade));
        assertThrows(BusinessException.class, () -> cidadeService().cadastrarCidade(getMockCidadeDTO()));
    }

    @Test
    void testarBuscarCidadesPorNome() {
        Cidade cidade = getMockCidade();
        when(cidadeRepository.findAllByNomeIgnoreCase(Mockito.any())).thenReturn(Collections.singletonList(cidade));
        List<Cidade> cidadeRetorno = cidadeService().buscarCidades(null, cidade.getNome());
        assertEquals(cidade.getNome(), cidadeRetorno.get(0).getNome());
    }

    @Test
    void testarBuscarCidadesFalhaParametros() {
        Cidade cidade = getMockCidade();
        when(cidadeRepository.findAllByNomeIgnoreCase(Mockito.any())).thenReturn(Collections.singletonList(cidade));
        assertThrows(BusinessException.class, () -> cidadeService().buscarCidades(cidade.getEstado(), cidade.getNome()));
    }

    @Test
    void testarBuscarCidadesFalhaParametrosNulos() {
        Cidade cidade = getMockCidade();
        when(cidadeRepository.findAllByNomeIgnoreCase(Mockito.any())).thenReturn(Collections.singletonList(cidade));
        assertThrows(BusinessException.class, () -> cidadeService().buscarCidades(null, null));
    }

    @Test
    void testarBuscarCidadeNaoEncontradoPeloNome() {
        String nomeCidade = getMockCidade().getNome();
        CidadeServiceImpl cidadeService = cidadeService();
        when(cidadeRepository.findByNomeIgnoreCase(Mockito.anyString())).thenReturn(null);
        List<Cidade> cidadeList = cidadeService.buscarCidades(null, nomeCidade);
        assertTrue(cidadeList.isEmpty());
    }

    @Test
    void testarBuscarCidadesPorEstado() {
        when(cidadeRepository.findAllByEstado(Mockito.any())).thenReturn(Collections.singletonList(getMockCidade()));
        List<Cidade> cidades = cidadeService().buscarCidades(EstadoEnum.MG, null);
        assertEquals(1, cidades.size());
    }

    @Test
    void testarBuscarCidadesSemSucessoPorEstado() {
        EstadoEnum estadoEnum = EstadoEnum.SP;
        CidadeServiceImpl cidadeService = cidadeService();
        when(cidadeRepository.findAllByEstado(Mockito.any())).thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class, () -> cidadeService.buscarCidades(EstadoEnum.MG,null));
    }

    @Test
    void testarConsultarCidadePeloID(){
        Cidade cidade = getMockCidade();
        when(cidadeRepository.findById(Mockito.any())).thenReturn(Optional.of(cidade));
        Cidade cidadeRetorno = cidadeService().consultarCidadePorId(cidade.getId());
        assertEquals(cidade.getId(), cidadeRetorno.getId());
    }
}
