package br.ifg.urt.barbearia_api.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
@Validated
@Tag(name = "Pagamentos", description = "Endpoints para processamento e consulta de pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    // Processar um novo pagamento
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Processar novo pagamento",
            description = "Registra um novo pagamento vinculado a um agendamento.",
            responses = {
                    @ApiResponse(description = "Criado com sucesso", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = PagamentoResponseDTO.class))),
                    @ApiResponse(description = "Erro de validação", responseCode = "400", content = @Content)
            }
    )
    // Limpa o cache de pagamentos sempre que um novo é adicionado
    @CacheEvict(value = "pagamentosCache", allEntries = true)
    public ResponseEntity<PagamentoResponseDTO> processar(@RequestBody @Valid PagamentoRequestDTO dto) {
        PagamentoResponseDTO response = pagamentoService.processarPagamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Listar todos os pagamentos com paginação
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar pagamentos paginados",
            description = "Retorna uma lista de pagamentos com suporte a paginação.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class)))
            }
    )
    // Cacheable: Melhora performance evitando consultas repetidas ao banco
    @Cacheable(value = "pagamentosCache", key = "{#pageable.pageNumber, #pageable.pageSize}")
    public ResponseEntity<Page<PagamentoResponseDTO>> listarTodos(
            // ParameterObject: Informa ao Swagger como ler os parâmetros de paginação
            // PageableDefault: Configura 10 itens por página, ordenados pela data de pagamento
            @ParameterObject @PageableDefault(size = 10, sort = "dataPagamento") Pageable pageable) {
        return ResponseEntity.ok(pagamentoService.listarTodos(pageable));
    }

    // Buscar pagamento por ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar pagamento por ID", description = "Retorna os detalhes de um pagamento único.")
    public ResponseEntity<PagamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pagamentoService.buscarPorId(id));
    }
}