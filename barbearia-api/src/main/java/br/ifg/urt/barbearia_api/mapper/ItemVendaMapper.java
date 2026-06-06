package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.ItemVendaRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ItemVendaResponseDTO;
import br.ifg.urt.barbearia_api.model.ItemVenda;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemVendaMapper {

    // Se o seu ItemVendaResponseDTO tiver o campo "produtoNome", descomente a linha abaixo.
    // Caso dê erro nele de novo, pode deixar essa linha inteira comentada/removida.
    // @Mapping(target = "produtoNome", source = "item.nome")
    ItemVendaResponseDTO toResponseDTO(ItemVenda itemVenda);

    List<ItemVendaResponseDTO> toResponseDTOList(List<ItemVenda> itensVenda);

    // Ignora a chave primária da entidade ao receber a requisição
    @Mapping(target = "idItemVenda", ignore = true)
    // Ignoramos os valores e o objeto do produto aqui, pois você irá associar e setar
    // esses valores direto na sua classe Service usando o dto.idItem()
    @Mapping(target = "valorUnitario", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    @Mapping(target = "item", ignore = true)
    ItemVenda toEntity(ItemVendaRequestDTO dto);
}