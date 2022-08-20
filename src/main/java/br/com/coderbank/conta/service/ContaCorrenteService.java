package br.com.coderbank.conta.service;

import br.com.coderbank.conta.domain.Cliente;
import br.com.coderbank.conta.domain.ContaCorrente;
import br.com.coderbank.conta.domain.Movimentacao;
import br.com.coderbank.conta.domain.enums.TipoMovimentacao;
import br.com.coderbank.conta.dto.ContaCorrenteDTO;
import br.com.coderbank.conta.dto.movimentacao.DepositoContaDTO;
import br.com.coderbank.conta.dto.movimentacao.MovimentacaoDTO;
import br.com.coderbank.conta.repository.ClienteRepository;
import br.com.coderbank.conta.repository.ContaCorrenteRepository;
import br.com.coderbank.conta.repository.MovimentacaoRepository;
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

    private final MovimentacaoRepository movimentacaoRepository;

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

    public Optional<MovimentacaoDTO> depositar(DepositoContaDTO depositoContaDTO) {
        Integer numeroConta = depositoContaDTO.getNumeroConta();
        BigDecimal valorDeposito = depositoContaDTO.getValor();

        var contaOptional = contaCorrenteRepository.findByNumeroConta(numeroConta);
        var contaEntity = contaOptional.get();

        contaEntity.adicionarSaldo(valorDeposito);

        contaCorrenteRepository.save(contaEntity);

        MovimentacaoDTO movimentacaoDTO = salvarMovimentacaoBancaria(valorDeposito, contaEntity);

        return Optional.of(movimentacaoDTO);
    }

    private MovimentacaoDTO salvarMovimentacaoBancaria(BigDecimal valorDeposito, ContaCorrente contaEntity) {
        var movimentacaoEntity = Movimentacao.builder()
                .valor(valorDeposito)
                .tipoMovimentacao(TipoMovimentacao.DEPOSITO)
                .build();

        movimentacaoEntity.setContaCorrente(contaEntity);
        movimentacaoRepository.save(movimentacaoEntity);

        var movimentacaoDTO = MovimentacaoDTO.builder()
                .idMovimentacao(movimentacaoEntity.getId())
                .idContaCorrente(movimentacaoEntity.getContaCorrente().getId())
                .tipoMovimentacao(movimentacaoEntity.getTipoMovimentacao().getDescricao())
                .valor(movimentacaoEntity.getValor())
                .build();
        return movimentacaoDTO;
    }
}
