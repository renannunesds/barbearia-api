package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.ProdutoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ProdutoResponseDTO;
import br.ifg.urt.barbearia_api.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    @Mapping(target = "idItem", source = "produto.idItem")
    @Mapping(target = "nome", source = "produto.nome")
    @Mapping(target = "descricao", source = "produto.descricao")
    @Mapping(target = "valor", source = "produto.valor")

    @Mapping(target = "quantidadeEstoqu", source = "produto.quantidadeEstoque")
    ProdutoResponseDTO toResponseDTO(Produto produto);

    List<ProdutoResponseDTO> toResponseDTOList(List<Produto> produtos);

    @Mapping(target = "idItem", ignore = true)
    @Mapping(target = "nome", source = "dto.nome")
    @Mapping(target = "descricao", source = "dto.descricao")
    @Mapping(target = "valor", source = "dto.valor")

    @Mapping(target = "quantidadeEstoque", source = "dto.quantidadeEstoque")
    Produto toEntity(ProdutoRequestDTO dto);
}