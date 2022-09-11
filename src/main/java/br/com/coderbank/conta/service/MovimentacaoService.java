package br.com.coderbank.conta.service;

import br.com.coderbank.conta.domain.ContaCorrente;
import br.com.coderbank.conta.domain.Movimentacao;
import br.com.coderbank.conta.domain.enums.TipoMovimentacao;
import br.com.coderbank.conta.dto.movimentacao.*;
import br.com.coderbank.conta.exceptions.CustomException;
import br.com.coderbank.conta.exceptions.ObjectNotFoundException;
import br.com.coderbank.conta.mapper.ContaCorrenteMapper;
import br.com.coderbank.conta.mapper.MovimentacaoMapper;
import br.com.coderbank.conta.repository.ContaCorrenteRepository;
import br.com.coderbank.conta.repository.MovimentacaoRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {
    private final ContaCorrenteRepository contaCorrenteRepository;

    private final MovimentacaoRepository movimentacaoRepository;
    
    private MovimentacaoMapper movimentacaoMapper = Mappers.getMapper(MovimentacaoMapper.class);;

    public Optional<MovimentacaoDTO> depositar(DepositoContaDTO depositoContaDTO) {
        Integer numeroConta = depositoContaDTO.getNumeroConta();
        BigDecimal valorDeposito = depositoContaDTO.getValor();

        ContaCorrente contaEntity = atualizarSaldoPositivoConta(numeroConta, valorDeposito);


        MovimentacaoDTO movimentacaoDTO = salvarMovimentacaoBancaria(valorDeposito, contaEntity, TipoMovimentacao.DEPOSITO);

        return Optional.of(movimentacaoDTO);
    }

    private ContaCorrente atualizarSaldoPositivoConta(Integer numeroConta, BigDecimal valorDeposito) {
        ContaCorrente contaEntity = obterConta(numeroConta);

        contaEntity.adicionarSaldo(valorDeposito);
        contaCorrenteRepository.save(contaEntity);
        return contaEntity;
    }

    public Optional<MovimentacaoDTO> sacar(SaqueContaDTO saqueContaDTO) {
        Integer numeroConta = saqueContaDTO.getNumeroConta();
        BigDecimal valorSaque = saqueContaDTO.getValor();

        ContaCorrente contaEntity = atualizarSaldoNegativoConta(numeroConta, valorSaque);

        MovimentacaoDTO movimentacaoDTO = salvarMovimentacaoBancaria(valorSaque, contaEntity, TipoMovimentacao.SAQUE);

        return Optional.of(movimentacaoDTO);
    }

    private ContaCorrente atualizarSaldoNegativoConta(Integer numeroConta, BigDecimal valorSaque) {
        ContaCorrente contaEntity = obterConta(numeroConta);

        contaEntity.removerSaldo(valorSaque);
        contaCorrenteRepository.save(contaEntity);
        return contaEntity;
    }

    private ContaCorrente obterConta(Integer numeroConta) {
        var contaEntity = contaCorrenteRepository.findByNumeroConta(numeroConta).orElseThrow(
                () -> {
                    throw new ObjectNotFoundException("Conta não encontrada para o número: " + numeroConta);
                }
        );
        return contaEntity;
    }


    public Optional<TransferenciaResponseDTO> transferir(TransferenciaRequestDTO transferenciaDTO) {
        Integer numeroContaOrigem = transferenciaDTO.getNumeroContaOrigem();
        Integer numeroContaDestino = transferenciaDTO.getNumeroContaDestino();
        BigDecimal valorTransferencia = transferenciaDTO.getValor();


        validarIgualdadeContas(numeroContaOrigem, numeroContaDestino);


        var contaOrigem = atualizarSaldoNegativoConta(numeroContaOrigem, valorTransferencia);
        atualizarSaldoPositivoConta(numeroContaDestino, valorTransferencia);

        salvarMovimentacaoBancaria(valorTransferencia, contaOrigem, TipoMovimentacao.TRANSFERENCIA);


        return Optional.of(builderTransferenciaResponse(numeroContaOrigem, numeroContaDestino, valorTransferencia));
    }

    private static TransferenciaResponseDTO builderTransferenciaResponse(Integer numeroContaOrigem, Integer numeroContaDestino, BigDecimal valorTransferencia) {
        return TransferenciaResponseDTO.builder().
                dataHoraProcessamento(LocalDateTime.now())
                .valor(valorTransferencia)
                .numeroContaOrigem(numeroContaOrigem)
                .numeroContaDestino(numeroContaDestino)
                .build();
    }

    private static void validarIgualdadeContas(Integer numeroContaOrigem, Integer numeroContaDestino) {
        if (Objects.equals(numeroContaOrigem, numeroContaDestino)) {
            throw new CustomException("As contas devem ser diferentes");
        }
    }


    private static Movimentacao builderMovimentacao(BigDecimal valor, ContaCorrente contaEntity, TipoMovimentacao tipoMovimentacao) {
        var movimentacaoEntity = Movimentacao.builder()
                .valor(valor)
                .tipoMovimentacao(tipoMovimentacao)
                .contaCorrente(contaEntity)
                .build();
        return movimentacaoEntity;
    }

    private MovimentacaoDTO salvarMovimentacaoBancaria(BigDecimal valor, ContaCorrente contaCorrente, TipoMovimentacao tipoMovimentacao) {
        Movimentacao movimentacaoEntity = builderMovimentacao(valor, contaCorrente, tipoMovimentacao);

        movimentacaoRepository.save(movimentacaoEntity);

        var movimentacaoDTO = MovimentacaoDTO.builder()
                .idMovimentacao(movimentacaoEntity.getId())
                .idContaCorrente(movimentacaoEntity.getContaCorrente().getId())
                .tipoMovimentacao(movimentacaoEntity.getTipoMovimentacao().getDescricao())
                .valor(movimentacaoEntity.getValor())
                .build();

        return movimentacaoDTO;
    }


    public List<MovimentacaoDTO> obterMovimentacoesConta(Integer numeroConta) {
        List<Movimentacao> movimentacaos = movimentacaoRepository.findByContaCorrente_NumeroConta(numeroConta);
    
        return movimentacaoMapper.toListaMovimentacaoDTO(movimentacaos);
    }
}
