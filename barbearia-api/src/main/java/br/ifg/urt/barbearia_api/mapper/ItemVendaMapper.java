package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.ItemVendaRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ItemVendaResponseDTO;
import br.ifg.urt.barbearia_api.model.ItemVenda;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemVendaMapper {

    // Mapeia atributos que vêm de objetos internos (ex: extrai o valor monetário e dados do produto)
    @Mapping(target = "valorUnitario", source = "valorUnitario.valor")
    @Mapping(target = "subtotal", source = "subtotal.valor")
    @Mapping(target = "produtoNome", source = "produto.nome")
    @Mapping(target = "valorFormatado", expression = "java(itemVenda.getValorUnitario() != null ? itemVenda.getValorUnitario().getFormatado() : null)")
    ItemVendaResponseDTO toResponseDTO(ItemVenda itemVenda);

    List<ItemVendaResponseDTO> toResponseDTOList(List<ItemVenda> itensVenda);

    // Ignora o ID gerado pelo banco e reconstrói os Value Objects usando expressões Java baseadas no DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "valorUnitario", expression = "java(new br.ifg.urt.barbearia_api.model.vo.PrecoVO(dto.valorUnitario()))")
    @Mapping(target = "produto", ignore = true) // Ignora o objeto Produto completo para ser associado via ID no Service
    ItemVenda toEntity(ItemVendaRequestDTO dto);
}
