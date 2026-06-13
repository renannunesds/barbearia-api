package br.ifg.urt.barbearia_api.assembler;

import br.ifg.urt.barbearia_api.controller.ClienteController;
import br.ifg.urt.barbearia_api.dto.response.ClienteResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClienteModelAssembler
        implements RepresentationModelAssembler<ClienteResponseDTO, EntityModel<ClienteResponseDTO>> {

    @Override
    public EntityModel<ClienteResponseDTO> toModel(ClienteResponseDTO dto) {
        EntityModel<ClienteResponseDTO> model = EntityModel.of(dto);

        // Link 'self': aponta para o GET por ID do cliente
        model.add(linkTo(methodOn(ClienteController.class).buscarPorId(dto.id())).withSelfRel());

        // Link relacional: aponta para a listagem paginada de clientes
        model.add(linkTo(methodOn(ClienteController.class).buscarTodos(null, null, null)).withRel("todos-clientes"));

        return model;
    }
}