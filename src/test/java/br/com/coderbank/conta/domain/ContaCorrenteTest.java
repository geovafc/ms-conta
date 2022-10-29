package br.com.coderbank.conta.domain;

import br.com.coderbank.conta.exceptions.CustomException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ContaCorrenteTest {


    @Test
    void deveAdicionarSaldoContaComSucesso() {
//        Cenário
        var saldoAtualFake = new BigDecimal(10);

        var contaCorrenteFake = ContaCorrente.builder()
                .saldo(saldoAtualFake)
                .build();

        var valorEntrada = new BigDecimal(110);

        var saldoEsperado = new BigDecimal(120);

//        Ação
        contaCorrenteFake.adicionarSaldo(valorEntrada);

//Validação
        var saldoAtual = contaCorrenteFake.getSaldo();

        assertEquals(saldoEsperado, saldoAtual);


    }

    @Test
    void deveValidarAssertivas() {
        boolean isAtivo =  true;

        assertTrue(isAtivo);

        boolean isAtivoFalse =  false;

        assertFalse(isAtivoFalse);

        ContaCorrente contaCorrente = null;
        assertNull(contaCorrente);

        ContaCorrente contaCorrenteNaoNulo = new ContaCorrente();
        assertNotNull(contaCorrenteNaoNulo);

        assertNotEquals("a", "b");
    }

    @Test
    void deveRemoverSaldoContaComSucesso() {
//        Cenário:
        var saldoInicialFake = new BigDecimal(100);

        var contaCorrenteFake = ContaCorrente.builder()
                .saldo(saldoInicialFake)
                .build();

        var valorEntrada = new BigDecimal(30);

        var saldoEsperado = new BigDecimal(70);

//        Ação:
        contaCorrenteFake.removerSaldo(valorEntrada);

//        Verificação
        var saldoAtual = contaCorrenteFake.getSaldo();
        assertEquals(saldoEsperado, saldoAtual);
    }

    @Test
    void deveLancarExceptionQuandoTentarDeixarSaldoMenorQueZero() {
//        Cenário
        var saldoInicialFake = new BigDecimal(100);

        var contaCorrenteFake = ContaCorrente.builder()
                .saldo(saldoInicialFake)
                .build();

        var valorEntrada = new BigDecimal(115);

        String mensagemEsperada = "Saldo insuficiente para realizar a operação";

//        Açaõ
        Exception excecaoRetornada = assertThrows(CustomException.class, ()-> {
            contaCorrenteFake.removerSaldo(valorEntrada);
        });

//        Validação
        String mensagemRetornadaExcecao = excecaoRetornada.getMessage();

        assertEquals(mensagemEsperada, mensagemRetornadaExcecao);
    }
}
