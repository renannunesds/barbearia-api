package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.assembler.ItemVendaModelAssembler;
import br.ifg.urt.barbearia_api.dto.request.ItemVendaRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ItemVendaResponseDTO;
import br.ifg.urt.barbearia_api.service.ItemVendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
@RequestMapping("/itens-venda")
@Validated
@Tag(name = "Itens de Venda", description = "Endpoints para gerenciamento dos itens vinculados a uma venda")
public class ItemVendaController {

    private final ItemVendaService service;
    private final ItemVendaModelAssembler assembler;

    public ItemVendaController(ItemVendaService service, ItemVendaModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Criar novo item de venda",
            responses = {
                    @ApiResponse(description = "Criado com sucesso", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = ItemVendaResponseDTO.class))),
                    @ApiResponse(description = "Erro de validação ou payload incorreto", responseCode = "400", content = @Content)
            }
    )
    @CacheEvict(value = "itensCache", allEntries = true)
    public ResponseEntity<EntityModel<ItemVendaResponseDTO>> criar(@Valid @RequestBody ItemVendaRequestDTO dto) {
        System.out.println("### CRIANDO NOVO ITEM DE VENDA... ###");
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(service.criar(dto)));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar itens de venda",
            description = "Retorna a lista completa de itens de venda cadastrados.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CollectionModel.class)))
            }
    )
    @Cacheable(value = "itensCache")
    public ResponseEntity<CollectionModel<EntityModel<ItemVendaResponseDTO>>> listar() {
        System.out.println("### CONSULTANDO ITENS DE VENDA NO BANCO DE DADOS... ###");

        List<EntityModel<ItemVendaResponseDTO>> itens = service.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(itens,
                linkTo(methodOn(ItemVendaController.class).listar()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Buscar item por ID",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ItemVendaResponseDTO.class))),
                    @ApiResponse(description = "Item não encontrado", responseCode = "404", content = @Content)
            }
    )
    public ResponseEntity<EntityModel<ItemVendaResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.buscarPorId(id)));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Atualizar item de venda",
            responses = {
                    @ApiResponse(description = "Atualizado com sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ItemVendaResponseDTO.class))),
                    @ApiResponse(description = "Item não encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Dados inválidos", responseCode = "400", content = @Content)
            }
    )
    @CacheEvict(value = "itensCache", allEntries = true)
    public ResponseEntity<EntityModel<ItemVendaResponseDTO>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ItemVendaRequestDTO dto) {
        return ResponseEntity.ok(assembler.toModel(service.atualizar(id, dto)));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir item de venda",
            responses = {
                    @ApiResponse(description = "Excluído com sucesso", responseCode = "204"),
                    @ApiResponse(description = "Item não encontrado", responseCode = "404", content = @Content)
            }
    )
    @CacheEvict(value = "itensCache", allEntries = true)
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}