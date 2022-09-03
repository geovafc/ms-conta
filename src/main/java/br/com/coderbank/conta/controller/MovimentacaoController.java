package br.com.coderbank.conta.controller;

import br.com.coderbank.conta.dto.movimentacao.DepositoContaDTO;
import br.com.coderbank.conta.service.MovimentacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
