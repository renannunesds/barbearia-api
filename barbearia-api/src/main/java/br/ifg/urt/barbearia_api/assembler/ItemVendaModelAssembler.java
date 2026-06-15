package br.ifg.urt.barbearia_api.assembler;

import br.ifg.urt.barbearia_api.controller.ItemVendaController;
import br.ifg.urt.barbearia_api.dto.response.ItemVendaResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ItemVendaModelAssembler implements RepresentationModelAssembler<ItemVendaResponseDTO, EntityModel<ItemVendaResponseDTO>> {
    @Override
    public EntityModel<ItemVendaResponseDTO> toModel(ItemVendaResponseDTO dto) {
        return EntityModel.of(dto,
                // 🔥 CORRIGIDO: Trocado de dto.id() para dto.idItemVenda()
                linkTo(methodOn(ItemVendaController.class).buscarPorId(dto.idItemVenda())).withSelfRel(),
                linkTo(methodOn(ItemVendaController.class).listar()).withRel("itens-venda"));
    }
}