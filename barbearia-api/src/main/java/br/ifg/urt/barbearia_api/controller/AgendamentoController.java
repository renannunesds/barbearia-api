package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.assembler.AgendamentoModelAssembler;
import br.ifg.urt.barbearia_api.dto.request.AgendamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.AgendamentoResponseDTO;
import br.ifg.urt.barbearia_api.service.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendamentos")
@Validated
@Tag(name = "Agendamentos", description = "Endpoints para gerenciamento de agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;
    private final AgendamentoModelAssembler assembler;

    public AgendamentoController(AgendamentoService agendamentoService, AgendamentoModelAssembler assembler) {
        this.agendamentoService = agendamentoService;
        this.assembler = assembler;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar novo agendamento", responses = {
            @ApiResponse(description = "Criado com sucesso", responseCode = "201", content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
            @ApiResponse(description = "Horário indisponível ou dados inválidos", responseCode = "400") // <--- Avisa o Swagger do erro 400
    })
    @CacheEvict(value = "agendamentosCache", allEntries = true)
    public ResponseEntity<EntityModel<AgendamentoResponseDTO>> criar(@RequestBody @Valid AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO response = agendamentoService.criarAgendamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(response));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable(value = "agendamentosCache", key = "{#pageable.pageNumber, #pageable.pageSize}")
    public ResponseEntity<PagedModel<EntityModel<AgendamentoResponseDTO>>> listarTodos(
            @ParameterObject @PageableDefault(size = 10, sort = "data") Pageable pageable,
            PagedResourcesAssembler<AgendamentoResponseDTO> pagedResourcesAssembler) {

        Page<AgendamentoResponseDTO> page = agendamentoService.listarTodos(pageable);

        return ResponseEntity.ok(pagedResourcesAssembler.toModel(page, (RepresentationModelAssembler) assembler));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<AgendamentoResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(agendamentoService.buscarPorId(id)));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "agendamentosCache", allEntries = true)
    public ResponseEntity<EntityModel<AgendamentoResponseDTO>> atualizar(@PathVariable Long id, @RequestBody @Valid AgendamentoRequestDTO dto) {
        return ResponseEntity.ok(assembler.toModel(agendamentoService.atualizar(id, dto)));
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "agendamentosCache", allEntries = true)
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}