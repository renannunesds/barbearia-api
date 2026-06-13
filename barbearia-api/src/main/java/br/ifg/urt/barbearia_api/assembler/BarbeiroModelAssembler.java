package br.ifg.urt.barbearia_api.assembler;

import br.ifg.urt.barbearia_api.controller.BarbeiroController;
import br.ifg.urt.barbearia_api.dto.response.BarbeiroResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BarbeiroModelAssembler
        implements RepresentationModelAssembler<BarbeiroResponseDTO, EntityModel<BarbeiroResponseDTO>> {

    @Override
    public EntityModel<BarbeiroResponseDTO> toModel(BarbeiroResponseDTO dto) {
        // Envelopa o seu DTO de resposta dentro do objeto de Hipermídia do Spring
        EntityModel<BarbeiroResponseDTO> model = EntityModel.of(dto);

        // Link Self: Cria dinamicamente a URL "http://localhost:8080/barbeiros/{id}"
        model.add(linkTo(methodOn(BarbeiroController.class).buscarPorId(dto.id())).withSelfRel());

        // Link Relacional: Aponta para o método de listagem paginada (buscarTodos)
        // Passamos 'null' nos parâmetros apenas para o Spring HATEOAS entender a assinatura do método do controller
        model.add(linkTo(methodOn(BarbeiroController.class).buscarTodos(null, null, null)).withRel("todos-barbeiros"));

        return model;
    }
}