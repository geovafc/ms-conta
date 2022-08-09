package br.com.coderbank.conta.service;

import br.com.coderbank.conta.domain.Cliente;
import br.com.coderbank.conta.domain.ContaCorrente;
import br.com.coderbank.conta.repository.ClienteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ContaCorrenteService {
    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;

    public void criarConta(String jsonCliente) throws JsonProcessingException {
        var cliente = objectMapper.readValue(jsonCliente, Cliente.class);

        var contaCorrente = buildContaCorrente();

        cliente.setConta(contaCorrente);

        clienteRepository.save(cliente);
    }

    private ContaCorrente buildContaCorrente() {
        var numeroContaAleatorio = new Random().nextLong();

        var contaCorrente = ContaCorrente.builder()
                .numeroAgencia(1l)
                .numeroConta(numeroContaAleatorio)
                .valor(BigDecimal.ZERO)
                .build();

        return contaCorrente;
    }
}
