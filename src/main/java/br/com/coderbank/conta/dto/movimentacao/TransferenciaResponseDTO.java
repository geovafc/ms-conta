package br.com.coderbank.conta.dto.movimentacao;

import br.com.coderbank.conta.dto.ContaCorrenteDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferenciaResponseDTO {
    private LocalDateTime dataHoraProcessamento;

    private BigDecimal valor;

    private Integer numeroContaOrigem;

    private Integer numeroContaDestino;

}
