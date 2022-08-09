package br.com.coderbank.conta.service;

import br.com.coderbank.conta.domain.Cliente;
import br.com.coderbank.conta.domain.ContaCorrente;
import br.com.coderbank.conta.repository.ContaCorrenteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContaCorrenteService {
    private ContaCorrenteRepository contaCorrenteRepository;
    private ObjectMapper objectMapper;

    public void criar(String clienteJson) throws JsonProcessingException {
        var cliente = objectMapper.readValue(clienteJson, Cliente.class);
//        Cria uma cont\
//        a para o cliente e salva no BD
//        var contaCorrente = ContaCorrente.builder()
//                .numeroAgencia(1)
////                para gerar numeros aleatorios, converter
////                a data atual para long
//                .numeroConta()
//
//        contaRepository.save()
    }
}
