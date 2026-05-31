package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.dto.request.AgendamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.AgendamentoResponseDTO;
import br.ifg.urt.barbearia_api.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos") // Define a rota base: http://localhost:8080/agendamentos
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> criar(@RequestBody @Valid AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO response = agendamentoService.criarAgendamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listarTodos() {
        List<AgendamentoResponseDTO> lista = agendamentoService.listarTodos();
        return ResponseEntity.ok(lista);
    }
}