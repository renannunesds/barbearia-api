package br.ifg.urt.barbearia_api.assembler;

import br.ifg.urt.barbearia_api.controller.EspecialidadeController;
import br.ifg.urt.barbearia_api.model.Especialidade;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EspecialidadeModelAssembler
        implements RepresentationModelAssembler<Especialidade, EntityModel<Especialidade>> {

    @Override
    public EntityModel<Especialidade> toModel(Especialidade entity) {
        EntityModel<Especialidade> model = EntityModel.of(entity);

        // Link 'self': aponta para o GET individual usando o getId() da sua Entidade
        model.add(linkTo(methodOn(EspecialidadeController.class).findById(entity.getId())).withSelfRel());

        // Link relacional: aponta para a listagem paginada (findAll) passando null nos parâmetros de assinatura
        model.add(linkTo(methodOn(EspecialidadeController.class).findAll(null, null, null)).withRel("todas-especialidades"));

        return model;
    }
}