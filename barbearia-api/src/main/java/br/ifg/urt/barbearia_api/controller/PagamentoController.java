package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.assembler.PagamentoModelAssembler;
import br.ifg.urt.barbearia_api.dto.request.PagamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.PagamentoResponseDTO;
import br.ifg.urt.barbearia_api.service.PagamentoService;
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
@RequestMapping("/pagamentos")
@Validated
@Tag(name = "Pagamentos", description = "Endpoints para gerenciamento e processamento de pagamentos da barbearia")
public class PagamentoController {

    private final PagamentoService pagamentoService;
    private final PagamentoModelAssembler assembler;

    public PagamentoController(PagamentoService pagamentoService, PagamentoModelAssembler assembler) {
        this.pagamentoService = pagamentoService;
        this.assembler = assembler;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Processar novo pagamento",
            description = "Efetua o processamento de um pagamento para uma venda ativa no sistema.",
            responses = {
                    @ApiResponse(description = "Pagamento processado com sucesso", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = PagamentoResponseDTO.class))),
                    @ApiResponse(description = "Erro de validação nos dados enviados", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Regra de negócio violada / Pagamento recusado", responseCode = "422", content = @Content)
            }
    )
    @CacheEvict(value = "pagamentosCache", allEntries = true)
    public ResponseEntity<EntityModel<PagamentoResponseDTO>> processar(@RequestBody @Valid PagamentoRequestDTO dto) {
        System.out.println("### PROCESSANDO NOVO PAGAMENTO... ###");
        PagamentoResponseDTO response = pagamentoService.processarPagamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(response));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar pagamentos paginados",
            description = "Retorna uma listagem paginada de todos os pagamentos registrados no sistema.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PagedModel.class))),
                    @ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)
            }
    )
    @Cacheable(value = "pagamentosCache", key = "{#pageable.pageNumber, #pageable.pageSize}")
    public ResponseEntity<PagedModel<EntityModel<PagamentoResponseDTO>>> listarTodos(
            @ParameterObject @PageableDefault(size = 10, sort = "dataPagamento") Pageable pageable,
            PagedResourcesAssembler<PagamentoResponseDTO> pagedResourcesAssembler) {

        System.out.println("### CONSULTANDO PAGAMENTOS NO BANCO DE DADOS... ###");
        Page<PagamentoResponseDTO> page = pagamentoService.listarTodos(pageable);


        return ResponseEntity.ok(pagedResourcesAssembler.toModel(page, assembler));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Buscar pagamento por ID",
            description = "Retorna os detalhes de um pagamento específico a partir do ID fornecido.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PagamentoResponseDTO.class))),
                    @ApiResponse(description = "Pagamento não encontrado", responseCode = "404", content = @Content)
            }
    )
    public ResponseEntity<EntityModel<PagamentoResponseDTO>> buscarPorId(@PathVariable Long id) {
        System.out.println("### BUSCANDO PAGAMENTO POR ID... ###");
        return ResponseEntity.ok(assembler.toModel(pagamentoService.buscarPorId(id)));
    }
}