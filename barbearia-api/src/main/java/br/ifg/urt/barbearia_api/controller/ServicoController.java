package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.assembler.ServicoModelAssembler;
import br.ifg.urt.barbearia_api.dto.request.ServicoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ServicoResponseDTO;
import br.ifg.urt.barbearia_api.service.ServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
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
@RequestMapping("/servicos")
@Validated
@Tag(name = "Serviços", description = "Endpoints para gerenciamento do catálogo de serviços oferecidos")
public class ServicoController {

    private final ServicoService servicoService;
    private final ServicoModelAssembler assembler;
    private final PagedResourcesAssembler<ServicoResponseDTO> pagedResourcesAssembler;

    public ServicoController(ServicoService servicoService,
                             ServicoModelAssembler assembler,
                             PagedResourcesAssembler<ServicoResponseDTO> pagedResourcesAssembler) {
        this.servicoService = servicoService;
        this.assembler = assembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar novo serviço", responses = {
            @ApiResponse(description = "Criado com sucesso", responseCode = "201", content = @Content(schema = @Schema(implementation = ServicoResponseDTO.class))),
            @ApiResponse(description = "Dados inválidos", responseCode = "400", content = @Content)
    })
    public ResponseEntity<EntityModel<ServicoResponseDTO>> criar(@Valid @RequestBody ServicoRequestDTO dto) {
        ServicoResponseDTO novoServico = servicoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(novoServico));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar serviços paginados", description = "Retorna uma listagem paginada dos serviços disponíveis.")
    public ResponseEntity<PagedModel<EntityModel<ServicoResponseDTO>>> listar(
            @RequestParam(required = false) String nome,
            @ParameterObject @PageableDefault(size = 10, sort = "nome") Pageable pageable,
            PagedResourcesAssembler<ServicoResponseDTO> pagedResourcesAssembler) {

        System.out.println("### CONSULTANDO SERVIÇOS NO BANCO DE DADOS... ###");
        Page<ServicoResponseDTO> page = servicoService.listar(nome, pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(page, assembler));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar serviço por ID")
    public ResponseEntity<EntityModel<ServicoResponseDTO>> buscarPorId(@PathVariable Long id) {
        ServicoResponseDTO dto = servicoService.buscarPorId(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar serviço")
    public ResponseEntity<EntityModel<ServicoResponseDTO>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ServicoRequestDTO dto) {
        ServicoResponseDTO atualizado = servicoService.atualizar(id, dto);
        return ResponseEntity.ok(assembler.toModel(atualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir serviço")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        servicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}