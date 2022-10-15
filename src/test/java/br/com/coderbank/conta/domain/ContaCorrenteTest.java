package br.com.coderbank.conta.domain;

import br.com.coderbank.conta.exceptions.CustomException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class ContaCorrenteTest {

    @Test
    void deveAdicionarSaldoConta() {
//        Cenário: é onde as variáveis e objetos são
//        iniciadas. Do que eu preciso para simular esse
//        teste? De uma ContaCorrente com dados preenchido
        var saldoInicialFake = new BigDecimal(10);
        var contaCorrenteFake = ContaCorrente
                .builder()
                .saldo(saldoInicialFake)
                .build();

        var saldoEsperado = new BigDecimal(120);

        var valorEntrada = new BigDecimal(110);


//        Ação: é a chamada para  o método que queremos testar. Quem
//        queremos testes ? Adicionar saldo da contaCorrente

        contaCorrenteFake.adicionarSaldo(valorEntrada);

//        Validação: é onde vamos verificar o resultado da ação, baseado no cenário
//        criado. É aqui que vamos verificar se o resultado da ação está de acordo
//        com o esperado. O que esperamos ? que seja adicionado um novo saldo na conta
//        com sucesso. Para isso usamos assertivas - afirmativas
        var saldoAtual = contaCorrenteFake.getSaldo();

        assertEquals(saldoEsperado, saldoAtual);
    }

    @Test
    void deveValidarAssertivas() {
        //        Tipos de assertivas - verificações
        boolean isTrue = true;
        assertTrue(isTrue);

        boolean isFalse = false;
        assertFalse(isFalse);

        ContaCorrente conta = null;
//        ContaCorrente conta = new ContaCorrente();
        assertNull(conta);

        ContaCorrente contaNaoNula = new ContaCorrente();
//        ContaCorrente contaNaoNula = null;
        assertNotNull(contaNaoNula);


        assertNotEquals("a", "b");
    }

    @Test
    void deveRemoverSaldoConta() {
//        Cenário: é onde as variáveis e objetos são
//        iniciadas. Do que eu preciso para simular esse
//        teste? De uma ContaCorrente com saldo maior que zero
        var saldoInicialFake = new BigDecimal(100);

        var contaCorrenteFake = ContaCorrente
                .builder()
                .saldo(saldoInicialFake)
                .build();

        var valorEntrada = new BigDecimal(30);

        var saldoEsperado = new BigDecimal(70);

//        Ação: é a chamada para  o método que queremos testar. Quem
//        queremos testar ? Remover saldo da contaCorrente

        contaCorrenteFake.removerSaldo(valorEntrada);

//        Validação: é onde vamos verificar o resultado da ação, baseado no cenário
//        criado. É aqui que vamos verificar se o resultado da ação está de acordo
//        com o esperado. O que esperamos ? que seja removido um valor do saldo da conta
//        com sucesso. Para isso usamos assertivas - afirmativas
        var saldoAtual = contaCorrenteFake.getSaldo();

        assertEquals(saldoEsperado, saldoAtual);
    }

    @Test
    void deveLancarExceptionQuandoTentarDeixarSaldoMenorQueZero() {
        //        Cenário: é onde as variáveis e objetos são
//        iniciadas. Do que eu preciso para simular esse
//        teste? De uma ContaCorrente com saldo maior que zero

        String mensagemEsperada = "Saldo insuficiente para realizar a operação";

        var saldoInicialFake = new BigDecimal(100);
        var contaCorrenteFake = ContaCorrente
                .builder()
                .saldo(saldoInicialFake)
                .build();

        var valorEntrada = new BigDecimal(110);

//        Ação: é a chamada para  o método que queremos testar. Quem
//        queremos testar ? Remover saldo da contaCorrente

//função lambda sem argumentos () -> {copo da função com várias instruções}
        Exception excecao = assertThrows(CustomException.class, ()-> {
            contaCorrenteFake.removerSaldo(valorEntrada);
        });

//        Validação: é onde vamos verificar o resultado da ação, baseado no cenário
//        criado. É aqui que vamos verificar se o resultado da ação está de acordo
//        com o esperado. O que esperamos ? que au tentar remover da conta um valor
//        superior ao saldo existente na conta, seje lançada uma exception.
//        Para isso usamos assertivas - afirmativas

        String mensagemRetornadaExcecao = excecao.getMessage();

        assertEquals(mensagemEsperada, mensagemRetornadaExcecao);
    }
}
