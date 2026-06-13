package br.ifg.urt.barbearia_api.assembler;

import br.ifg.urt.barbearia_api.controller.UsuarioController;
import br.ifg.urt.barbearia_api.dto.response.UsuarioResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioModelAssembler
        implements RepresentationModelAssembler<UsuarioResponseDTO, EntityModel<UsuarioResponseDTO>> {

    @Override
    public EntityModel<UsuarioResponseDTO> toModel(UsuarioResponseDTO dto) {
        EntityModel<UsuarioResponseDTO> model = EntityModel.of(dto);

        // Link 'self': Aponta para a consulta por ID deste usuário específico
        model.add(linkTo(methodOn(UsuarioController.class).buscarPorId(dto.id())).withSelfRel());

        // CORREÇÃO AQUI: Passamos dois nulos 'null, null' porque o seu controller espera 2 parâmetros agora!
        model.add(linkTo(methodOn(UsuarioController.class).buscarTodos(null, null)).withRel("todos-usuarios"));

        return model;
    }
}