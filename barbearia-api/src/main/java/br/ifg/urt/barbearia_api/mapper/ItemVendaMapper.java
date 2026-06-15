package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.ItemVendaRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ItemVendaResponseDTO;
import br.ifg.urt.barbearia_api.model.ItemVenda;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemVendaMapper {


    @Mapping(source = "item.idItem", target = "idItem")
    @Mapping(source = "item.nome", target = "nomeItem")
    ItemVendaResponseDTO toResponseDTO(ItemVenda itemVenda);

    List<ItemVendaResponseDTO> toResponseDTOList(List<ItemVenda> itensVenda);

    @Mapping(target = "idItemVenda", ignore = true)
    @Mapping(target = "valorUnitario", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    @Mapping(target = "item", ignore = true)
    ItemVenda toEntity(ItemVendaRequestDTO dto);
}