package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.dto.request.ClienteRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ClienteResponseDTO;
import br.ifg.urt.barbearia_api.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@Validated
@Tag(name = "Clientes", description = "Endpoints para gerenciamento do cadastro de clientes da barbearia")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    // Buscar todos
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar todos os clientes",
            description = "Retorna uma lista com todos os clientes cadastrados na base de dados.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClienteResponseDTO.class)))),
                    @ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<List<ClienteResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(service.findAll());
    }

    // Buscar por ID
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

    // Criar cliente
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

    // Atualizar cliente
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

    // Deletar cliente
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