package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.dto.request.PagamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.PagamentoResponseDTO;
import br.ifg.urt.barbearia_api.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pagamentos") // Define a rota base: http://localhost:8080/pagamentos
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> processar(@RequestBody @Valid PagamentoRequestDTO dto) {
        PagamentoResponseDTO response = pagamentoService.processarPagamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}