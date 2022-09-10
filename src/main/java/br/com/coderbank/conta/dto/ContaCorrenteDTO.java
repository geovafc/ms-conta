package br.com.coderbank.conta.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContaCorrenteDTO {

    private UUID id;

    private UUID idCliente;

    private Integer numeroAgencia;

    private Integer numeroConta;

    private BigDecimal saldo;


}
