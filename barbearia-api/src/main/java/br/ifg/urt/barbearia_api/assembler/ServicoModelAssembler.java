package br.ifg.urt.barbearia_api.assembler;

import br.ifg.urt.barbearia_api.controller.ServicoController;
import br.ifg.urt.barbearia_api.dto.response.ServicoResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ServicoModelAssembler implements RepresentationModelAssembler<ServicoResponseDTO, EntityModel<ServicoResponseDTO>> {

    @Override
    public EntityModel<ServicoResponseDTO> toModel(ServicoResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ServicoController.class).buscarPorId(dto.idItem())).withSelfRel(),
                linkTo(methodOn(ServicoController.class).listar(null, Pageable.unpaged(), null)).withRel("servicos"));
    }
}