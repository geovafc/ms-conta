package br.com.coderbank.conta.controller;

import br.com.coderbank.conta.domain.ContaCorrente;
import br.com.coderbank.conta.dto.ContaCorrenteDTO;
import br.com.coderbank.conta.dto.movimentacao.DepositoContaDTO;
import br.com.coderbank.conta.exceptions.ObjectNotFoundException;
import br.com.coderbank.conta.service.ContaCorrenteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/contas")
@Slf4j
@RequiredArgsConstructor
public class ContaCorrenteController {
    private final ContaCorrenteService contaCorrenteService;


    @GetMapping
    public ResponseEntity<List<ContaCorrenteDTO>> obterContas() {
        log.info("Requisição REST para obter todas as contas");

        return ResponseEntity.status(HttpStatus.OK)
                .body(contaCorrenteService.obterContas());
    }

    @GetMapping("/clientes/{cpf}")
    public ResponseEntity<Object> obterContaCliente(@PathVariable(value = "cpf") String cpf) {
        log.info("Requisição REST para obter dados da conta de um cliente pelo cpf : {}", cpf);

        Optional<ContaCorrenteDTO> contaCorrenteDTOOptional = contaCorrenteService.obterContaCliente(cpf);

//        return ResponseEntity.status(HttpStatus.OK).body(contaCorrenteDTOOptional.get());

        return contaCorrenteDTOOptional.<ResponseEntity<Object>>map(
                        contaCorrenteDTO -> ResponseEntity.status(HttpStatus.OK).body(contaCorrenteDTO))
                .orElseThrow(
                        () -> {

                            log.error("Objeto não encontrado {}", cpf);

                            throw new ObjectNotFoundException("Objeto não encontrado " + cpf);

                        }
                );
    }
}
