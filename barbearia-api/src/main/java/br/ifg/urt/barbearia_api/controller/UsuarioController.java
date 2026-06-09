package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.dto.request.UsuarioRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.UsuarioResponseDTO;
import br.ifg.urt.barbearia_api.service.UsuarioService;
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
@RequestMapping("/usuarios")
@Validated
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de acessos e usuários administrativos/funcionários do sistema")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    // Buscar todos
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar todos os usuários",
            description = "Retorna uma listagem completa dos perfis de usuários que possuem credenciais no sistema.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDTO.class)))),
                    @ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<List<UsuarioResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(service.findAll());
    }

    // Buscar por ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna as credenciais e dados públicos de um usuário com base em seu ID.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(description = "Usuário não encontrado", responseCode = "404", content = @Content)
            }
    )
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // Criar usuário
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Criar novo usuário",
            description = "Cadastra uma nova credencial (com usuário e senha) para dar acesso ao sistema.",
            responses = {
                    @ApiResponse(description = "Criado com sucesso", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(description = "Erro de validação ou payload incorreto", responseCode = "400", content = @Content)
            }
    )
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO novoUsuario = service.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novoUsuario);
    }

    // Atualizar usuário
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Atualizar usuário",
            description = "Altera os dados cadastrais ou permissões de um determinado usuário por meio do seu ID único.",
            responses = {
                    @ApiResponse(description = "Atualizado com sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(description = "Usuário não encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Dados inválidos", responseCode = "400", content = @Content)
            }
    )
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir usuário",
            description = "Revoga o acesso e exclui em definitivo o usuário selecionado do sistema de controle.",
            responses = {
                    @ApiResponse(description = "Excluído com sucesso", responseCode = "204"),
                    @ApiResponse(description = "Usuário não encontrado", responseCode = "404", content = @Content)
            }
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}