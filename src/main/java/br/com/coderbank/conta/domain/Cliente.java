package br.com.coderbank.conta.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CB_CLIENTE")
public class Cliente implements Serializable {
    //controller das conversões feitas pela JVM
    private static final long serialVersionUID = 1L;

    @Id
//Identificador Único Universal

//UUId são identificadores únicos que podem ser gerados em qualquer lugar
// são próprios para arquiteturas distribuídas, evitando conflitos que gerariam
// se fossem sequenciais
    private UUID id;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    // Todas as operações de persist, merge, remove, refresh serão
// também realizadas para o objeto filho (conta). Quando salvar
// o cliente, também será salvo uma conta para o cliente, a mesma
// coisa para atualização e delecao
    @OneToOne(cascade = CascadeType.ALL)
// Define o nome da coluna que possui o id da conta associada
// ao cliente
    @JoinColumn(name = "conta_id", referencedColumnName = "id")
    private ContaCorrente conta;
}
