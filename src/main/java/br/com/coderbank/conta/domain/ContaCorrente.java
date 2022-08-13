package br.com.coderbank.conta.domain;


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
@Entity
@Table(name = "CB_CONTA_CORRENTE")
public class ContaCorrente implements Serializable {
    //controller das conversões feitas pela JVM
    private static final long serialVersionUID = 1L;

    @Id
//    AUTO - gerado de forma automatica
    @GeneratedValue(strategy = GenerationType.AUTO)
//Identificador Único Universal

//UUId são identificadores únicos que podem ser gerados em qualquer lugar
// são próprios para arquiteturas distribuídas, evitando conflitos que gerariam
// se fossem sequenciais

//    Permite trabalhar com UUID no mysql e h2
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(nullable = false)
    private Integer numeroAgencia;

    @Column(nullable = false, unique = true)
    private Integer numeroConta;

    @Column(nullable = false)
    private BigDecimal saldo;

}
