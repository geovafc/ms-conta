package br.com.coderbank.conta.repository;

import br.com.coderbank.conta.domain.ContaCorrente;
import br.com.coderbank.conta.domain.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, UUID> {

    List<Movimentacao> findByContaCorrente_NumeroConta(Integer numeroConta);

}
