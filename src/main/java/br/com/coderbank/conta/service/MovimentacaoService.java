package br.com.coderbank.conta.service;

import br.com.coderbank.conta.domain.ContaCorrente;
import br.com.coderbank.conta.domain.Movimentacao;
import br.com.coderbank.conta.domain.enums.TipoMovimentacao;
import br.com.coderbank.conta.dto.movimentacao.DepositoContaDTO;
import br.com.coderbank.conta.dto.movimentacao.MovimentacaoDTO;
import br.com.coderbank.conta.dto.movimentacao.SaqueContaDTO;
import br.com.coderbank.conta.exceptions.ObjectNotFoundException;
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

        Movimentacao movimentacaoEntity = builderMovimentacao(valorDeposito, contaEntity, TipoMovimentacao.DEPOSITO);

        MovimentacaoDTO movimentacaoDTO = salvarMovimentacaoBancaria(movimentacaoEntity);

        return Optional.of(movimentacaoDTO);
    }

    private ContaCorrente atualizarSaldoPositivoConta(Integer numeroConta, BigDecimal valorDeposito) {
        var contaEntity = contaCorrenteRepository.findByNumeroConta(numeroConta).orElseThrow(
                () -> {
                    throw new ObjectNotFoundException("Conta não encontrada para o número: " + numeroConta );
                }
        );

        contaEntity.adicionarSaldo(valorDeposito);
        contaCorrenteRepository.save(contaEntity);
        return contaEntity;
    }

    public Optional<MovimentacaoDTO> sacar(SaqueContaDTO saqueContaDTO) {
        Integer numeroConta = saqueContaDTO.getNumeroConta();
        BigDecimal valorSaque = saqueContaDTO.getValor();

        ContaCorrente contaEntity = atualizarSaldoNegativoConta(numeroConta, valorSaque);

        Movimentacao movimentacaoEntity = builderMovimentacao(valorSaque, contaEntity, TipoMovimentacao.SAQUE);

        MovimentacaoDTO movimentacaoDTO = salvarMovimentacaoBancaria(movimentacaoEntity);

        return Optional.of(movimentacaoDTO);
    }

    private ContaCorrente atualizarSaldoNegativoConta(Integer numeroConta, BigDecimal valorSaque) {
        var contaEntity = contaCorrenteRepository.findByNumeroConta(numeroConta).orElseThrow(
                () -> {
                    throw new ObjectNotFoundException("Conta não encontrada para o número: " + numeroConta );
                }
        );

        contaEntity.removerSaldo(valorSaque);
        contaCorrenteRepository.save(contaEntity);
        return contaEntity;
    }


    private static Movimentacao builderMovimentacao(BigDecimal valorDeposito, ContaCorrente contaEntity, TipoMovimentacao tipoMovimentacao) {
        var movimentacaoEntity = Movimentacao.builder()
                .valor(valorDeposito)
                .tipoMovimentacao(tipoMovimentacao)
                .contaCorrente(contaEntity)
                .build();
        return movimentacaoEntity;
    }

    private MovimentacaoDTO salvarMovimentacaoBancaria(Movimentacao movimentacaoEntity) {

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
