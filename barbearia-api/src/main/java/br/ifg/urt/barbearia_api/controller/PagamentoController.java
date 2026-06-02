package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.dto.request.PagamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.PagamentoResponseDTO;
import br.ifg.urt.barbearia_api.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@Validated
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    // Processar pagamento
    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> processar(
            @RequestBody @Valid PagamentoRequestDTO dto) {

        PagamentoResponseDTO response = pagamentoService.processarPagamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Buscar todos os pagamentos
    @GetMapping
    public ResponseEntity<List<PagamentoResponseDTO>> listarTodos() {
        // CORRIGIDO: Alterado de buscarTodos() para listarTodos() para alinhar com o seu Service
        return ResponseEntity.ok(pagamentoService.listarTodos());
    }

    // Buscar pagamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(pagamentoService.buscarPorId(id));
    }
}