package br.com.coderbank.conta.mapper;

import br.com.coderbank.conta.domain.ContaCorrente;
import br.com.coderbank.conta.dto.ContaCorrenteDTO;
import org.mapstruct.Mapper;

import java.util.List;

//Interface responsável pelas declarações do mapeamento
// Para a interface ser gerenciada pelo spring eu uso componentModel="spring"
@Mapper(componentModel = "spring")
public interface ContaCorrenteMapper {

    ContaCorrenteDTO toContaCorrenteDTO(ContaCorrente contaCorrente);

    List<ContaCorrenteDTO> toListaContaCorrenteDTO(List<ContaCorrente> contaCorrente);
}
