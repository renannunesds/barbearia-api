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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendamentos")
@Validated
@Tag(name = "Agendamentos", description = "Endpoints para gerenciamento de horários e agendamentos da barbearia")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;
    private final AgendamentoModelAssembler assembler;

    public AgendamentoController(AgendamentoService agendamentoService, AgendamentoModelAssembler assembler) {
        this.agendamentoService = agendamentoService;
        this.assembler = assembler;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Criar novo agendamento",
            description = "Registra um novo agendamento de horário para um cliente na barbearia.",
            responses = {
                    @ApiResponse(description = "Criado com sucesso", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
                    @ApiResponse(description = "Horário indisponível ou dados inválidos", responseCode = "400", content = @Content)
            }
    )
    @CacheEvict(value = "agendamentosCache", allEntries = true)
    public ResponseEntity<EntityModel<AgendamentoResponseDTO>> criar(@RequestBody @Valid AgendamentoRequestDTO dto) {
        System.out.println("### CRIANDO NOVO AGENDAMENTO... ###");
        AgendamentoResponseDTO response = agendamentoService.criarAgendamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(response));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar agendamentos paginados",
            description = "Retorna uma listagem paginada de todos os agendamentos cadastrados no sistema.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PagedModel.class))),
                    @ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)
            }
    )
    @Cacheable(value = "agendamentosCache", key = "{#pageable.pageNumber, #pageable.pageSize}")
    public ResponseEntity<PagedModel<EntityModel<AgendamentoResponseDTO>>> listarTodos(
            @ParameterObject @PageableDefault(size = 10, sort = "data") Pageable pageable,
            PagedResourcesAssembler<AgendamentoResponseDTO> pagedResourcesAssembler) {

        System.out.println("### CONSULTANDO AGENDAMENTOS NO BANCO DE DADOS... ###");
        Page<AgendamentoResponseDTO> page = agendamentoService.listarTodos(pageable);


        return ResponseEntity.ok(pagedResourcesAssembler.toModel(page, assembler));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Buscar agendamento por ID",
            description = "Retorna os detalhes de um agendamento específico baseado no ID fornecido.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
                    @ApiResponse(description = "Agendamento não encontrado", responseCode = "404", content = @Content)
            }
    )
    public ResponseEntity<EntityModel<AgendamentoResponseDTO>> buscarPorId(@PathVariable Long id) {
        System.out.println("### BUSCANDO AGENDAMENTO POR ID... ###");
        return ResponseEntity.ok(assembler.toModel(agendamentoService.buscarPorId(id)));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Atualizar agendamento",
            description = "Atualiza completamente as informações de um agendamento existente (como mudança de data, horário ou profissional).",
            responses = {
                    @ApiResponse(description = "Atualizado com sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
                    @ApiResponse(description = "Agendamento não encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Dados inválidos ou horário indisponível", responseCode = "400", content = @Content)
            }
    )
    @CacheEvict(value = "agendamentosCache", allEntries = true)
    public ResponseEntity<EntityModel<AgendamentoResponseDTO>> atualizar(@PathVariable Long id, @RequestBody @Valid AgendamentoRequestDTO dto) {
        System.out.println("### ATUALIZANDO AGENDAMENTO... ###");
        return ResponseEntity.ok(assembler.toModel(agendamentoService.atualizar(id, dto)));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir agendamento",
            description = "Cancela e remove permanentemente o registro de um agendamento do sistema.",
            responses = {
                    @ApiResponse(description = "Excluído com sucesso", responseCode = "204"),
                    @ApiResponse(description = "Agendamento não encontrado", responseCode = "404", content = @Content)
            }
    )
    @CacheEvict(value = "agendamentosCache", allEntries = true)
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        System.out.println("### DELETANDO AGENDAMENTO... ###");
        agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}