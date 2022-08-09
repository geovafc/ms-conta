package br.com.coderbank.conta.repository;

import br.com.coderbank.conta.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
}
