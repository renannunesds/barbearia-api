package br.ifg.urt.barbearia_api.assembler;

import br.ifg.urt.barbearia_api.controller.ServicoController;
import br.ifg.urt.barbearia_api.dto.response.ServicoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ServicoModelAssembler
        implements RepresentationModelAssembler<ServicoResponseDTO, EntityModel<ServicoResponseDTO>> {

    @Override
    public EntityModel<ServicoResponseDTO> toModel(ServicoResponseDTO dto) {
        EntityModel<ServicoResponseDTO> model = EntityModel.of(dto);

        // Link 'self': Aponta para a busca individual usando o idItem gerado
        model.add(linkTo(methodOn(ServicoController.class).buscarPorId(dto.idItem())).withSelfRel());

        // Link relacional: Aponta para a listagem paginada que corrigimos no Controller
        model.add(linkTo(methodOn(ServicoController.class).listar(null, null)).withRel("todos-servicos"));

        return model;
    }
}