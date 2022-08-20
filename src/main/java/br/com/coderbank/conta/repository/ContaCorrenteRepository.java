package br.com.coderbank.conta.repository;

import br.com.coderbank.conta.domain.ContaCorrente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, UUID> {

    Optional<ContaCorrente> findByNumeroConta(Integer numeroConta);

}
