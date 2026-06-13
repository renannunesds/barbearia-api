package br.ifg.urt.barbearia_api.assembler;

import br.ifg.urt.barbearia_api.controller.ItemVendaController;
import br.ifg.urt.barbearia_api.dto.response.ItemVendaResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ItemVendaModelAssembler
        implements RepresentationModelAssembler<ItemVendaResponseDTO, EntityModel<ItemVendaResponseDTO>> {

    @Override
    public EntityModel<ItemVendaResponseDTO> toModel(ItemVendaResponseDTO dto) {
        EntityModel<ItemVendaResponseDTO> model = EntityModel.of(dto);

        // Link 'self': Aponta para a rota individual do item de venda específico
        model.add(linkTo(methodOn(ItemVendaController.class).buscarPorId(dto.idItemVenda())).withSelfRel());

        // Link relacional: Aponta para a listagem de itens gravados
        model.add(linkTo(methodOn(ItemVendaController.class).listar()).withRel("todos-itens-venda"));

        return model;
    }
}