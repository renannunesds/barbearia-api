package br.ifg.urt.barbearia_api.assembler;

import br.ifg.urt.barbearia_api.controller.AgendamentoController;
import br.ifg.urt.barbearia_api.dto.response.AgendamentoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AgendamentoModelAssembler implements RepresentationModelAssembler<AgendamentoResponseDTO, EntityModel<AgendamentoResponseDTO>> {

    @Override
    public EntityModel<AgendamentoResponseDTO> toModel(AgendamentoResponseDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(AgendamentoController.class).buscarPorId(entity.idAgendamento())).withSelfRel(),
                linkTo(methodOn(AgendamentoController.class).listarTodos(null, null)).withRel("agendamentos"));
    }
}