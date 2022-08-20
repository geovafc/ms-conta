package br.com.coderbank.conta.domain.enums;

import lombok.Getter;

@Getter
public enum TipoMovimentacao {
    DEPOSITO("Depósito"),
    SAQUE("Saque"),
    TRANSFERENCIA("Transferência");

    private String descricao;

    TipoMovimentacao(String descricao){
        this.descricao = descricao;
    }
}
