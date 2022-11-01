package br.com.coderbank.conta.service;

import br.com.coderbank.conta.domain.Cliente;
import br.com.coderbank.conta.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.UUID;

public class ContaCorrenteServiceTest {
    @Inject

    @Mock
    private final ClienteRepository clienteRepository;


    @Test
    void deveCriarConta() {
//        Cenário: criação das variáveis e objetos que vamos precisar
//        O que eu preciso para poder simular esse teste ?
//        json com as informações do cliente

//        var jsonCliente = "{ \"id\":\"23e4567-e89b-12d3-a456-426614174000\", \"nome\":\"Bill Gates\", \"cpf\":\"64949645080\" }";
        var clienteFake  = Cliente.builder()
                .id(UUID.fromString("23e4567-e89b-12d3-a456-426614174000"))
                .build();

//        Ação

    }
}
