package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.dto.request.BarbeiroRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.BarbeiroResponseDTO;
import br.ifg.urt.barbearia_api.service.BarbeiroService;
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
@RequestMapping("/barbeiros")
@Validated
@Tag(name = "Barbeiros", description = "Endpoints para gerenciamento do cadastro e equipe de barbeiros")
public class BarbeiroController {

    private final BarbeiroService service;

    public BarbeiroController(BarbeiroService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar barbeiros paginados",
            description = "Retorna uma página de barbeiros com suas respectivas especialidades. Permite filtrar por nome.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))),
                    @ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)
            }
    )
    @Cacheable(value = "barbeirosCache", key = "{#nome, #pageable.pageNumber, #pageable.pageSize}")
    public ResponseEntity<Page<BarbeiroResponseDTO>> buscarTodos(
            @RequestParam(required = false) String nome,
            @ParameterObject @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        System.out.println("### CONSULTANDO BARBEIROS NO BANCO DE DADOS... ###");
        return ResponseEntity.ok(service.findAll(nome, pageable));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Buscar barbeiro por ID",
            description = "Retorna os detalhes de um barbeiro específico passando o seu identificador único.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = BarbeiroResponseDTO.class))),
                    @ApiResponse(description = "Barbeiro não encontrado", responseCode = "404", content = @Content)
            }
    )
    public ResponseEntity<BarbeiroResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Criar novo barbeiro",
            description = "Cadastra um novo barbeiro no sistema vinculando-o aos IDs de especialidades existentes.",
            responses = {
                    @ApiResponse(description = "Criado com sucesso", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = BarbeiroResponseDTO.class))),
                    @ApiResponse(description = "Erro de validação nos dados", responseCode = "400", content = @Content)
            }
    )
    @CacheEvict(value = "barbeirosCache", allEntries = true)
    public ResponseEntity<BarbeiroResponseDTO> criar(@Valid @RequestBody BarbeiroRequestDTO dto) {
        BarbeiroResponseDTO novoBarbeiro = service.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novoBarbeiro);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Atualizar barbeiro",
            description = "Atualiza por completo as informações cadastrais e especialidades de um barbeiro existente.",
            responses = {
                    @ApiResponse(description = "Atualizado com sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = BarbeiroResponseDTO.class))),
                    @ApiResponse(description = "Barbeiro não encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Dados inválidos", responseCode = "400", content = @Content)
            }
    )
    @CacheEvict(value = "barbeirosCache", allEntries = true)
    public ResponseEntity<BarbeiroResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody BarbeiroRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PatchMapping("/{id}/ativar")
    @Operation(
            summary = "Ativar barbeiro",
            description = "Altera o status do barbeiro para ativo no sistema, permitindo novos agendamentos.",
            responses = {
                    @ApiResponse(description = "Barbeiro ativado com sucesso", responseCode = "204"),
                    @ApiResponse(description = "Barbeiro não encontrado", responseCode = "404", content = @Content)
            }
    )
    @CacheEvict(value = "barbeirosCache", allEntries = true)
    public ResponseEntity<Void> activarBarbeiro(@PathVariable Long id) {
        service.ativarBarbeiro(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desativar")
    @Operation(
            summary = "Desativar barbeiro",
            description = "Altera o status do barbeiro para inativo, bloqueando a criação de agendamentos temporariamente.",
            responses = {
                    @ApiResponse(description = "Barbeiro desativado com sucesso", responseCode = "204"),
                    @ApiResponse(description = "Barbeiro não encontrado", responseCode = "404", content = @Content)
            }
    )
    @CacheEvict(value = "barbeirosCache", allEntries = true)
    public ResponseEntity<Void> desativarBarbeiro(@PathVariable Long id) {
        service.desativarBarbeiro(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir barbeiro",
            description = "Remove o registro do barbeiro permanentemente ou logicamente da base de dados.",
            responses = {
                    @ApiResponse(description = "Excluído com sucesso", responseCode = "204"),
                    @ApiResponse(description = "Barbeiro não encontrado", responseCode = "404", content = @Content)
            }
    )
    @CacheEvict(value = "barbeirosCache", allEntries = true)
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}