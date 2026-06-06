package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.ProdutoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ProdutoResponseDTO;
import br.ifg.urt.barbearia_api.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    // Como Produto e ProdutoResponseDTO compartilham os mesmos nomes de atributos (ex: nome, valor),
    // o MapStruct mapeia tudo automaticamente.
    ProdutoResponseDTO toResponseDTO(Produto produto);

    List<ProdutoResponseDTO> toResponseDTOList(List<Produto> produtos);

    // Corrigido para ignorar o nome real da propriedade: idItem
    @Mapping(target = "idItem", ignore = true)
    Produto toEntity(ProdutoRequestDTO dto);
}