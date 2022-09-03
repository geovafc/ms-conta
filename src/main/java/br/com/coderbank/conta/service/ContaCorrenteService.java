package br.com.coderbank.conta.service;

import br.com.coderbank.conta.domain.Cliente;
import br.com.coderbank.conta.domain.ContaCorrente;
import br.com.coderbank.conta.dto.ContaCorrenteDTO;
import br.com.coderbank.conta.repository.ClienteRepository;
import br.com.coderbank.conta.repository.ContaCorrenteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ContaCorrenteService {
    private final ClienteRepository clienteRepository;
    private final ContaCorrenteRepository contaCorrenteRepository;

    private final ObjectMapper objectMapper;

    public void criarConta(String jsonCliente) throws JsonProcessingException {
        var cliente = objectMapper.readValue(jsonCliente, Cliente.class);

        var contaCorrente = buildContaCorrente();

        cliente.setConta(contaCorrente);

        clienteRepository.save(cliente);
    }

    private ContaCorrente buildContaCorrente() {
        var numeroContaAleatorio = new Random().nextInt(100000);

        var contaCorrente = ContaCorrente.builder()
                .numeroAgencia(1)
                .numeroConta(numeroContaAleatorio)
                .saldo(BigDecimal.ZERO)
                .build();

        return contaCorrente;
    }

    public List<ContaCorrente> obterContas() {

        return contaCorrenteRepository.findAll();
    }

    public Optional<ContaCorrenteDTO> obterContaCliente(String cpf) {

        var clienteOptional = clienteRepository.findByCpf(cpf);

//        Map permite transformar o valor do Optional.
//        Estamos trabalhando com o optional que tem o valor do cliente e queremos
//        que ele tenha o valor de ContaCorrenteDTO

//        neste exemplo estamos executando uma ação no cliente encontrado
//        estamos pegando os valores de cliente (this), passando para o método builder e
//        retornando um novo valor para o optional que no caso seria o ContaCorrenteDTO

//        MOSTRAR O JEITO COM 3 LINHAS
//        Optional<ContaCorrenteDTO> contaCorrenteDTOOptional = Optional.empty();
//        if (clienteOptional.isPresent()) {
//            contaCorrenteDTOOptional = Optional.ofNullable(buildercontaCorrenteDTO(clienteOptional.get()));
//        }


//        Optional<ContaCorrenteDTO> contaCorrenteDTO = clienteOptional.map(cliente -> buildercontaCorrenteDTO(cliente));

        Optional<ContaCorrenteDTO> contaCorrenteDTO = clienteOptional.map(this::buildercontaCorrenteDTO);

        return contaCorrenteDTO;
    }

    private ContaCorrenteDTO buildercontaCorrenteDTO(Cliente cliente) {
        var contaCorrente = cliente.getConta();

        return (ContaCorrenteDTO.builder()
                .id(contaCorrente.getId())
                .numeroConta(contaCorrente.getNumeroConta())
                .numeroAgencia(contaCorrente.getNumeroAgencia())
                .saldo(contaCorrente.getSaldo())
                .build());
    }

}
