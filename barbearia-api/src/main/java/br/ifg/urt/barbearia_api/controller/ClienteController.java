package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.dto.request.ClienteRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ClienteResponseDTO;
import br.ifg.urt.barbearia_api.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@Validated
@Tag(name = "Clientes", description = "Endpoints para gerenciamento do cadastro de clientes da barbearia")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    // ATUALIZADO COM FILTRO E PAGINAÇÃO
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar clientes com paginação e filtro",
            description = "Retorna uma página de clientes. Permite filtrar por nome e utilizar paginação.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))),
                    @ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<Page<ClienteResponseDTO>> buscarTodos(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(service.findAll(nome, pageable));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Buscar cliente por ID",
            description = "Retorna os dados detalhados de um cliente específico com base no ID fornecido.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))),
                    @ApiResponse(description = "Cliente não encontrado", responseCode = "404", content = @Content)
            }
    )
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Criar novo cliente",
            description = "Efetua o cadastro de um novo cliente e retorna o registro criado com seu respectivo ID.",
            responses = {
                    @ApiResponse(description = "Criado com sucesso", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))),
                    @ApiResponse(description = "Dados inválidos enviados", responseCode = "400", content = @Content)
            }
    )
    public ResponseEntity<ClienteResponseDTO> criar(@Valid @RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO novoCliente = service.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novoCliente);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Atualizar cliente",
            description = "Atualiza completamente as informações cadastrais de um cliente existente na base de dados.",
            responses = {
                    @ApiResponse(description = "Atualizado com sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))),
                    @ApiResponse(description = "Cliente não encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Dados inválidos", responseCode = "400", content = @Content)
            }
    )
    public ResponseEntity<ClienteResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir cliente",
            description = "Remove o registro de um cliente permanentemente ou altera seu estado lógico no sistema.",
            responses = {
                    @ApiResponse(description = "Excluído com sucesso", responseCode = "204"),
                    @ApiResponse(description = "Cliente não encontrado", responseCode = "404", content = @Content)
            }
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}