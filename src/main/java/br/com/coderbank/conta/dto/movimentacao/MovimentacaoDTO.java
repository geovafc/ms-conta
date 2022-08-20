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
public class MovimentacaoDTO {
    private UUID idMovimentacao;

    private UUID idContaCorrente;

    private BigDecimal valor;

    private String tipoMovimentacao;

}
