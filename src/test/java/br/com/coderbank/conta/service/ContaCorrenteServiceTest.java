package br.com.coderbank.conta.service;

import br.com.coderbank.conta.domain.Cliente;
import br.com.coderbank.conta.domain.ContaCorrente;
import br.com.coderbank.conta.dto.ContaCorrenteDTO;
import br.com.coderbank.conta.repository.ClienteRepository;
import br.com.coderbank.conta.repository.ContaCorrenteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContaCorrenteServiceTest {

    @InjectMocks
    private ContaCorrenteService contaCorrenteService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ContaCorrenteRepository contaCorrenteRepository;

    @Test
    void deveCriarContaComSucesso() throws JsonProcessingException {
//        Cenário

        String jsonClienteFakeEntrada = "{\n" +
                "\"id\": \"23e4567-e89b-12d3-a456-426614174000,\"\n" +
                "\"cpf\": \"217271287,\"\n" +
                "\"nome\": \"Francisco Santos\"\n" +
                "}";

        var clienteFakeEntrada = Cliente.builder()
                .id(UUID.fromString("23e4567-e89b-12d3-a456-426614174000"))
                .cpf("217271287").nome("Francisco Santos")
                .build();

        when(objectMapper.readValue(jsonClienteFakeEntrada, Cliente.class)).thenReturn(clienteFakeEntrada);
//        Ação

        contaCorrenteService.criarConta(jsonClienteFakeEntrada);

//        Validação

        verify(clienteRepository, times(1)).save(clienteFakeEntrada);

    }

    @Test
    void deveObterTodasAsContasComSucesso() {
//        Cenário
        var contasFakeRetornadaBancoDados = Arrays.asList(
                ContaCorrente.builder()
                        .id(UUID.fromString("23e4567-e89b-12d3-a456-426614174000"))
                        .build(),
                ContaCorrente.builder()
                        .id(UUID.fromString("23e4567-e89b-12d3-a456-426614174000"))
                        .build()
        );

        var contasFakeDTOEsperada = Arrays.asList(
                ContaCorrenteDTO.builder()
                        .id(UUID.fromString("23e4567-e89b-12d3-a456-426614174000"))
                        .build(),
                ContaCorrenteDTO.builder()
                        .id(UUID.fromString("23e4567-e89b-12d3-a456-426614174000"))
                        .build()
        );

        when(contaCorrenteRepository.findAll())
                .thenReturn(contasFakeRetornadaBancoDados);

//        Ação

       var contasDTORetornada =  contaCorrenteService.obterContas();

//      Validação

        assertEquals(contasFakeDTOEsperada, contasDTORetornada);


    }
}
