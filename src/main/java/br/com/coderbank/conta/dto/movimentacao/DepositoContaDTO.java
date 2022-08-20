package br.com.coderbank.conta.dto.movimentacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositoContaDTO {
    private Integer numeroConta;

    private BigDecimal valor;

}
