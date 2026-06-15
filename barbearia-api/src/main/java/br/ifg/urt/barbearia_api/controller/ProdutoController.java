package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.assembler.ProdutoModelAssembler;
import br.ifg.urt.barbearia_api.dto.request.ProdutoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ProdutoResponseDTO;
import br.ifg.urt.barbearia_api.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/produtos")
@Validated
@Tag(name = "Produtos", description = "Endpoints para gerenciamento do estoque de produtos comerciais")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoModelAssembler assembler;

    public ProdutoController(ProdutoService produtoService, ProdutoModelAssembler assembler) {
        this.produtoService = produtoService;
        this.assembler = assembler;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Criar novo produto",
            responses = {
                    @ApiResponse(description = "Criado com sucesso", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = ProdutoResponseDTO.class))),
                    @ApiResponse(description = "Erro de validação ou payload incorreto", responseCode = "400", content = @Content)
            }
    )
    public ResponseEntity<EntityModel<ProdutoResponseDTO>> criar(@Valid @RequestBody ProdutoRequestDTO dto) {
        ProdutoResponseDTO novoProduto = produtoService.criar(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assembler.toModel(novoProduto));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar todos os produtos", description = "Retorna o catálogo completo de produtos com hipermídias.")
    public ResponseEntity<CollectionModel<EntityModel<ProdutoResponseDTO>>> listar() {
        System.out.println("### CONSULTANDO PRODUTOS NO BANCO DE DADOS... ###");
        List<EntityModel<ProdutoResponseDTO>> produtos = produtoService.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(produtos,
                linkTo(methodOn(ProdutoController.class).listar()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar produto por ID")
    public ResponseEntity<EntityModel<ProdutoResponseDTO>> buscarPorId(@PathVariable Long id) {
        ProdutoResponseDTO dto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar produto por completo")
    public ResponseEntity<EntityModel<ProdutoResponseDTO>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoRequestDTO dto) {
        ProdutoResponseDTO atualizado = produtoService.atualizar(id, dto);
        return ResponseEntity.ok(assembler.toModel(atualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir produto")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}