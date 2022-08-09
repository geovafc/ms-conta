package br.com.coderbank.conta.controller;

import br.com.coderbank.conta.domain.ContaCorrente;
import br.com.coderbank.conta.service.ContaCorrenteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contas")
@Slf4j
@RequiredArgsConstructor
public class ContaCorrenteController {
    private final ContaCorrenteService contaCorrenteService;

    @GetMapping
    public ResponseEntity<List<ContaCorrente>> obterContas() {
        log.info("Requisição REST para obter todas as contas");

        return ResponseEntity.status(HttpStatus.OK)
                .body(contaCorrenteService.obterContas());
    }
}
