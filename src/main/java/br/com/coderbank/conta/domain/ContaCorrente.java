package br.com.coderbank.conta.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

//mappedBy:  informa quem é o pai desta relação.
//Deve ser o mesmo nome usado na outra ponta do relacionamento
    @OneToMany(mappedBy = "contaCorrente")
    private List<Movimentacao> movimentacoes = new ArrayList<>();

    public void adicionarSaldo(BigDecimal valor){
        this.saldo = this.saldo.add(valor);
    }

    public void removerSaldo(BigDecimal valor){
        this.saldo = this.saldo.subtract(valor);
// Se o resultado do saldo comparado com zero for -1, quer dizer que
// o saldo é menor que zero
//        if (this.saldo.compareTo(BigDecimal.ZERO) == -1) throw new CustomException()

    }


}
