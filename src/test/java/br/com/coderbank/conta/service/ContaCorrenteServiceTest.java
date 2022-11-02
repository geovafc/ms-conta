package br.com.coderbank.conta.service;

import br.com.coderbank.conta.domain.Cliente;
import br.com.coderbank.conta.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContaCorrenteServiceTest {
    @InjectMocks
    private ContaCorrenteService contaCorrenteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    void deveCriarConta() throws IOException {
//        Cenário: criação das variáveis e objetos fakes que vamos precisar
//        O que eu preciso para poder simular esse teste ?
//        preciso de um cliente fake gerado quando eu chamar objectMapper

        var clienteFake = Cliente.builder()
                .id(UUID.fromString("23e4567-e89b-12d3-a456-426614174000"))
                .build();

        when(objectMapper.readValue(anyString(), eq(Cliente.class))).thenReturn(clienteFake);

//        Ação: é a chamada para  o método que queremos testar. Quem
//        queremos testar ? criarConta

        contaCorrenteService.criarConta("algumObjetoJsonDeCliente");

//        Validação: é onde vamos verificar o resultado da ação, baseado no cenário
//        criado. É aqui que vamos verificar se o resultado da ação está de acordo
//        com o esperado.
//        O que esperamos ? que o clienteRepository seja chamado
//        para criar um cliente

        verify(clienteRepository, times(1)).save(clienteFake);
    }
}
