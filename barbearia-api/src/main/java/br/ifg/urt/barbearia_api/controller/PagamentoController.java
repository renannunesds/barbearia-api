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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@Validated
@Tag(name = "Pagamentos", description = "Endpoints para processamento e consulta de pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    // Processar pagamento
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Processar novo pagamento",
            description = "Registra e processa um pagamento associado a um agendamento da barbearia.",
            responses = {
                    @ApiResponse(description = "Criado com sucesso", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = PagamentoResponseDTO.class))),
                    @ApiResponse(description = "Erro de validação nos dados", responseCode = "400", content = @Content)
            }
    )
    @CacheEvict(value = "pagamentosCache", allEntries = true)
    public ResponseEntity<PagamentoResponseDTO> processar(
            @RequestBody @Valid PagamentoRequestDTO dto) {

        PagamentoResponseDTO response = pagamentoService.processarPagamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Buscar todos os pagamentos
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar todos os pagamentos",
            description = "Retorna uma lista com todos os pagamentos realizados no sistema.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = List.class))),
                    @ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)
            }
    )
    @Cacheable(value = "pagamentosCache")
    public ResponseEntity<List<PagamentoResponseDTO>> listarTodos() {

        return ResponseEntity.ok(pagamentoService.listarTodos());
    }

    // Buscar pagamento por ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Buscar pagamento por ID",
            description = "Retorna os detalhes de um pagamento específico passando o seu identificador único.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PagamentoResponseDTO.class))),
                    @ApiResponse(description = "Pagamento não encontrado", responseCode = "404", content = @Content)
            }
    )
    public ResponseEntity<PagamentoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(pagamentoService.buscarPorId(id));
    }
}