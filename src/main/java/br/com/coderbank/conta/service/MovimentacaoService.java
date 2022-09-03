package br.com.coderbank.conta.service;

import br.com.coderbank.conta.domain.ContaCorrente;
import br.com.coderbank.conta.domain.Movimentacao;
import br.com.coderbank.conta.domain.enums.TipoMovimentacao;
import br.com.coderbank.conta.dto.movimentacao.DepositoContaDTO;
import br.com.coderbank.conta.dto.movimentacao.MovimentacaoDTO;
import br.com.coderbank.conta.repository.ContaCorrenteRepository;
import br.com.coderbank.conta.repository.MovimentacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {
    private final ContaCorrenteRepository contaCorrenteRepository;

    private final MovimentacaoRepository movimentacaoRepository;



    public Optional<MovimentacaoDTO> depositar(DepositoContaDTO depositoContaDTO) {
        Integer numeroConta = depositoContaDTO.getNumeroConta();
        BigDecimal valorDeposito = depositoContaDTO.getValor();

        ContaCorrente contaEntity = atualizarSaldoPositivoConta(numeroConta, valorDeposito);

        MovimentacaoDTO movimentacaoDTO = salvarMovimentacaoBancaria(valorDeposito, contaEntity);

        return Optional.of(movimentacaoDTO);
    }

    private ContaCorrente atualizarSaldoPositivoConta(Integer numeroConta, BigDecimal valorDeposito) {
        var contaOptional = contaCorrenteRepository.findByNumeroConta(numeroConta);
        var contaEntity = contaOptional.get();

        contaEntity.adicionarSaldo(valorDeposito);
        contaCorrenteRepository.save(contaEntity);
        return contaEntity;
    }

    private MovimentacaoDTO salvarMovimentacaoBancaria(BigDecimal valorDeposito, ContaCorrente contaEntity) {
        var movimentacaoEntity = Movimentacao.builder()
                .valor(valorDeposito)
                .tipoMovimentacao(TipoMovimentacao.DEPOSITO)
                .contaCorrente(contaEntity)
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
