package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.ProdutoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ProdutoResponseDTO;
import br.ifg.urt.barbearia_api.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    @Mapping(target = "valor", source = "valor.valor")
    @Mapping(target = "valorFormatado", expression = "java(produto.getValor() != null ? produto.getValor().getFormatado() : null)")
    ProdutoResponseDTO toResponseDTO(Produto produto);

    List<ProdutoResponseDTO> toResponseDTOList(List<Produto> produtos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "valor", expression = "java(new br.ifg.urt.barbearia_api.model.vo.PrecoVO(dto.valor()))")
    Produto toEntity(ProdutoRequestDTO dto);
}