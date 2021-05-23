package com.compasso.uol.services;

import com.compasso.uol.config.ModelMapperConfig;
import com.compasso.uol.dtos.ClienteDTO;
import com.compasso.uol.exceptions.BusinessException;
import com.compasso.uol.exceptions.ResourceNotFoundException;
import com.compasso.uol.models.Cidade;
import com.compasso.uol.models.Cliente;
import com.compasso.uol.repositories.CidadeRepository;
import com.compasso.uol.repositories.ClienteRepository;
import com.compasso.uol.services.impl.ClienteServiceImpl;
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
import static org.mockito.Mockito.*;

@SpringBootTest
class ClienteServiceTest {

    private static final String MOCK_FOLDER = "mocks";
    private static final String MOCK_CLIENTE = "Cliente.json";
    private static final String MOCK_CLIENTE_DTO = "ClienteDTO.json";
    private static final String MOCK_CIDADE = "Cidade.json";

    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private CidadeService cidadeService;
    @Mock
    private CidadeRepository cidadeRepository;

    private final ModelMapper mapper = new ModelMapperConfig().modelMapper();

    private ClienteService clienteService() {
        return new ClienteServiceImpl(clienteRepository, mapper, cidadeService);
    }

    static Cliente getMockCliente() {
        return TestUtils.getMock(MOCK_FOLDER, MOCK_CLIENTE, Cliente.class);
    }

    static ClienteDTO getMockClienteDTO() {
        return TestUtils.getMock(MOCK_FOLDER, MOCK_CLIENTE_DTO, ClienteDTO.class);
    }

    static Cidade getMockCidade() {
        return TestUtils.getMock(MOCK_FOLDER, MOCK_CIDADE, Cidade.class);
    }

    @Test
    void testarSalvarCliente() {
        Cliente cliente = getMockCliente();
        Cidade cidade = getMockCidade();
        cliente.setCidade(cidade);
        when(clienteRepository.save(Mockito.any())).thenReturn(cliente);
        when(cidadeRepository.findById(Mockito.any())).thenReturn(Optional.of(cidade));
        Cliente clienteSalvo = clienteService().cadastrarCliente(getMockClienteDTO());
        assertEquals(getMockCliente().getId(), clienteSalvo.getId());
        assertEquals(getMockCliente().getCidade().getId(), clienteSalvo.getCidade().getId());
        assertEquals(getMockCliente().getDataNascimento(), clienteSalvo.getDataNascimento());
        assertEquals(getMockCliente().getSexo(), clienteSalvo.getSexo());
        assertEquals(getMockCliente().getNomeCompleto(), clienteSalvo.getNomeCompleto());
    }

    @Test
    void testarConsultarClientePorId() {
        Cliente cliente = getMockCliente();
        when(clienteRepository.findById(Mockito.any())).thenReturn(Optional.of(cliente));
        Cliente clienteRetorno = clienteService().consultarClientePorId(cliente.getId());
        assertEquals(clienteRetorno.getNomeCompleto(), cliente.getNomeCompleto());
        assertEquals(clienteRetorno.getId(), cliente.getId());
    }

    @Test
    void testarConsultarClientePorIdFalha() {
        Cliente cliente = getMockCliente();
        when(clienteRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> clienteService().consultarClientePorId(cliente.getId()));
    }

    @Test
    void testarConsultarClientePorNome() {
        List<Cliente> clientes = Collections.singletonList(getMockCliente());
        when(clienteRepository.findAllByNomeCompletoIgnoreCase(Mockito.anyString())).thenReturn(clientes);
        List<Cliente> clientesRetorno = clienteService().consultarClientePorNome(clientes.get(0).getNomeCompleto());
        assertEquals(clientesRetorno.get(0).getNomeCompleto(), clientes.get(0).getNomeCompleto());
        assertEquals(clientesRetorno.get(0).getId(), clientes.get(0).getId());
    }

    @Test
    void testarConsultarClientePorNomeFalha() {
        Cliente cliente = getMockCliente();
        when(clienteRepository.findAllByNomeCompletoIgnoreCase(Mockito.anyString())).thenReturn(Collections.emptyList());
        List<Cliente> clienteList = clienteService().consultarClientePorNome(cliente.getNomeCompleto());
        assertTrue(clienteList.isEmpty());
    }

    @Test
    void testarDeletarCliente() {
        Cliente cliente = getMockCliente();
        when(clienteRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(cliente));
        clienteService().deletarCliente(cliente.getId());
        verify(clienteRepository, Mockito.times(1)).delete(cliente);
    }

    @Test
    void testarAlterarNomeCliente() {
        Cliente cliente = getMockCliente();
        when(clienteRepository.findById(Mockito.any())).thenReturn(Optional.of(cliente));
        Cliente clienteRetorno = clienteService().alterarNomeCliente(cliente.getId(), "Fake Change Data Name");
        assertEquals(clienteRetorno.getNomeCompleto(), cliente.getNomeCompleto());
    }

    @Test
    void testarAlterarNomeClienteIgualNomeAnterior() {
        Cliente cliente = getMockCliente();
        when(clienteRepository.findById(Mockito.any())).thenReturn(Optional.of(cliente));
        assertThrows(BusinessException.class, () -> clienteService().alterarNomeCliente(cliente.getId(), cliente.getNomeCompleto()));
    }
}
