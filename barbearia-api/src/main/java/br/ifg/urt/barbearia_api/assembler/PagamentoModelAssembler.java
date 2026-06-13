package br.ifg.urt.barbearia_api.assembler;

import br.ifg.urt.barbearia_api.controller.PagamentoController;
import br.ifg.urt.barbearia_api.dto.response.PagamentoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PagamentoModelAssembler implements RepresentationModelAssembler<PagamentoResponseDTO, EntityModel<PagamentoResponseDTO>> {

    @Override
    public EntityModel<PagamentoResponseDTO> toModel(PagamentoResponseDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(PagamentoController.class).buscarPorId(entity.idPagamento())).withSelfRel(),
                linkTo(methodOn(PagamentoController.class).listarTodos(null, null)).withRel("pagamentos"));
    }
}