package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.assembler.UsuarioModelAssembler;
import br.ifg.urt.barbearia_api.dto.request.UsuarioRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.UsuarioResponseDTO;
import br.ifg.urt.barbearia_api.service.UsuarioService;
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
@RequestMapping("/usuarios")
@Validated
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de acessos e usuários administrativos/funcionários do sistema")
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioModelAssembler assembler; // 1. Injetando seu assembler customizado

    // Construtor atualizado recebendo o assembler
    public UsuarioController(UsuarioService service, UsuarioModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar usuários paginados",
            description = "Retorna uma listagem paginada dos perfis de usuários com seus respectivos links HATEOAS.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PagedModel.class))),
                    @ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)
            }
    )
    @Cacheable(value = "usuariosCache", key = "{#pageable.pageNumber, #pageable.pageSize}")
    public ResponseEntity<PagedModel<EntityModel<UsuarioResponseDTO>>> buscarTodos(
            @ParameterObject @PageableDefault(size = 10, sort = "nome") Pageable pageable,
            PagedResourcesAssembler<UsuarioResponseDTO> pagedResourcesAssembler) { // 2. Adicionado o gerenciador de páginas nativo do HATEOAS

        System.out.println("### CONSULTANDO USUÁRIOS NO BANCO DE DADOS... ###");
        Page<UsuarioResponseDTO> usuariosPage = service.findAll(pageable);

        // 3. Transforma o Page comum no PagedModel inteligente com links "first", "next", etc.
        PagedModel<EntityModel<UsuarioResponseDTO>> pagedModel = pagedResourcesAssembler.toModel(usuariosPage, assembler);

        return ResponseEntity.ok(pagedModel);
    }

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
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> buscarPorId(@PathVariable Long id) {
        UsuarioResponseDTO dto = service.findById(id);
        // Envelopa a resposta gerando os links automáticos
        return ResponseEntity.ok(assembler.toModel(dto));
    }

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
    @CacheEvict(value = "usuariosCache", allEntries = true)
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> criar(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO novoUsuario = service.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assembler.toModel(novoUsuario));
    }

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
    @CacheEvict(value = "usuariosCache", allEntries = true)
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO usuarioAtualizado = service.update(id, dto);
        return ResponseEntity.ok(assembler.toModel(usuarioAtualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir usuário",
            description = "Revoga o acesso e exclui em definitivo o usuário selecionado do sistema de controle.",
            responses = {
                    @ApiResponse(description = "Excluído com sucesso", responseCode = "204"),
                    @ApiResponse(description = "Usuário não encontrado", responseCode = "404", content = @Content)
            }
    )
    @CacheEvict(value = "usuariosCache", allEntries = true)
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}