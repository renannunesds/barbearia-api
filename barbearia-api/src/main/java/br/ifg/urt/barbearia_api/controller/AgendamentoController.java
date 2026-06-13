package br.ifg.urt.barbearia_api.controller;

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

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Criar novo agendamento",
            description = "Registra um novo agendamento no sistema vinculando cliente, barbeiro e serviço.",
            responses = {
                    @ApiResponse(description = "Criado com sucesso", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
                    @ApiResponse(description = "Erro de validação nos dados", responseCode = "400", content = @Content)
            }
    )
    @CacheEvict(value = "agendamentosCache", allEntries = true)
    public ResponseEntity<AgendamentoResponseDTO> criar(
            @RequestBody @Valid AgendamentoRequestDTO dto) {

        AgendamentoResponseDTO response = agendamentoService.criarAgendamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar agendamentos paginados",
            description = "Retorna uma página de agendamentos cadastrados no sistema.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))),
                    @ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)
            }
    )
    @Cacheable(value = "agendamentosCache", key = "{#pageable.pageNumber, #pageable.pageSize}")
    public ResponseEntity<Page<AgendamentoResponseDTO>> listarTodos(
            @ParameterObject @PageableDefault(size = 10, sort = "data") Pageable pageable) {

        return ResponseEntity.ok(agendamentoService.listarTodos(pageable));
    }

    // Buscar agendamento por ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Buscar agendamento por ID",
            description = "Retorna os detalhes de um agendamento específico passando o seu identificador único.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
                    @ApiResponse(description = "Agendamento não encontrado", responseCode = "404", content = @Content)
            }
    )
    public ResponseEntity<AgendamentoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(agendamentoService.buscarPorId(id));
    }

    // Atualizar/Remarcar agendamento
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Atualizar agendamento",
            description = "Atualiza por completo as informações de um agendamento existente por meio do ID.",
            responses = {
                    @ApiResponse(description = "Atualizado com sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
                    @ApiResponse(description = "Agendamento não encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Dados inválidos", responseCode = "400", content = @Content)
            }
    )
    @CacheEvict(value = "agendamentosCache", allEntries = true)
    public ResponseEntity<AgendamentoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid AgendamentoRequestDTO dto) {

        return ResponseEntity.ok(agendamentoService.atualizar(id, dto));
    }

    // Cancelar/Deletar agendamento
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir agendamento",
            description = "Remove o registro do agendamento permanentemente da base de dados.",
            responses = {
                    @ApiResponse(description = "Excluído com sucesso", responseCode = "204"),
                    @ApiResponse(description = "Agendamento não encontrado", responseCode = "404", content = @Content)
            }
    )
    @CacheEvict(value = "agendamentosCache", allEntries = true)
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}