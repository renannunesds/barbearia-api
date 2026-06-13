package br.ifg.urt.barbearia_api.assembler;

import br.ifg.urt.barbearia_api.controller.ProdutoController;
import br.ifg.urt.barbearia_api.dto.response.ProdutoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProdutoModelAssembler
        implements RepresentationModelAssembler<ProdutoResponseDTO, EntityModel<ProdutoResponseDTO>> {

    @Override
    public EntityModel<ProdutoResponseDTO> toModel(ProdutoResponseDTO dto) {
        EntityModel<ProdutoResponseDTO> model = EntityModel.of(dto);

        // Link 'self': Aponta para a rota GET por ID do produto
        model.add(linkTo(methodOn(ProdutoController.class).buscarPorId(dto.idItem())).withSelfRel());

        // Link relacional: Aponta para o método de listar geral
        model.add(linkTo(methodOn(ProdutoController.class).listar()).withRel("todos-produtos"));

        return model;
    }
}