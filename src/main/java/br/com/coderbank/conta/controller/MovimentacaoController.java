package br.com.coderbank.conta.controller;

import br.com.coderbank.conta.dto.ContaCorrenteDTO;
import br.com.coderbank.conta.dto.movimentacao.DepositoContaDTO;
import br.com.coderbank.conta.dto.movimentacao.MovimentacaoDTO;
import br.com.coderbank.conta.dto.movimentacao.SaqueContaDTO;
import br.com.coderbank.conta.dto.movimentacao.TransferenciaRequestDTO;
import br.com.coderbank.conta.service.MovimentacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimentacoes")
@Slf4j
@RequiredArgsConstructor
public class MovimentacaoController {
    private final MovimentacaoService movimentacaoService;

    @PostMapping("/depositos")
    public ResponseEntity<Object> depositar(@RequestBody DepositoContaDTO depositoContaDTO) {
        log.info("Requisição REST para depositar saldo na conta : {}", depositoContaDTO);

        var movimentacaoDTO = movimentacaoService.depositar(depositoContaDTO);

        return ResponseEntity.
                status(HttpStatus.OK)
                .body(movimentacaoDTO);
    }

    @PostMapping("/saques")
    public ResponseEntity<Object> sacar(@RequestBody SaqueContaDTO saqueContaDTO) {
        log.info("Requisição REST para sacar valor da conta : {}", saqueContaDTO);

        var movimentacaoDTO = movimentacaoService.sacar(saqueContaDTO);

        return ResponseEntity.
                status(HttpStatus.OK)
                .body(movimentacaoDTO);
    }

    @PostMapping("/transferencias")
    public ResponseEntity<Object> transferir(@RequestBody TransferenciaRequestDTO transferenciaRequestDTO) {
        log.info("Requisição REST para transferir valor entre contas: {}", transferenciaRequestDTO);

        var transferenciaResponseDTO = movimentacaoService.transferir(transferenciaRequestDTO);

        return ResponseEntity.
                status(HttpStatus.OK)
                .body(transferenciaResponseDTO);
    }

    @GetMapping("/{numeroConta}")
    public ResponseEntity<List<MovimentacaoDTO>> obterMovimentacoesConta(@PathVariable(value = "numeroConta") Integer numeroConta) {
        log.info("Requisição REST para obter movimentações da conta : {}", numeroConta);

        return ResponseEntity.status(HttpStatus.OK)
                .body(movimentacaoService.obterMovimentacoesConta(numeroConta));
    }
}
