package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.ServicoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ServicoResponseDTO;
import br.ifg.urt.barbearia_api.model.Servico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
// Exemplo para o ServicoMapper (faça o mesmo raciocínio no ProdutoMapper)
@Mapper(componentModel = "spring")
public interface ServicoMapper {

    // Se o nome do campo na Entidade e no DTO for apenas "valor",
    // e ambos forem compatíveis, você não precisa de nenhuma anotação aqui!
    ServicoResponseDTO toResponseDTO(Servico servico);

    List<ServicoResponseDTO> toResponseDTOList(List<Servico> servicos);

    // Corrigido para ignorar "idItem" (que é o nome real na sua entidade)
    @Mapping(target = "idItem", ignore = true)
    // Como a entidade espera um BigDecimal no valor, recebemos o BigDecimal do DTO diretamente
    @Mapping(target = "valor", source = "valor")
    Servico toEntity(ServicoRequestDTO dto);
}