package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.dto.request.AgendamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.AgendamentoResponseDTO;
import br.ifg.urt.barbearia_api.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@Validated // Alinhando com a validação do seu colega
public class AgendamentoController {

    // 1. Mudança para 'final' e injeção por construtor (Melhor prática de mercado)
    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    // Criar agendamento
    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> criar(
            @RequestBody @Valid AgendamentoRequestDTO dto) {

        AgendamentoResponseDTO response = agendamentoService.criarAgendamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listarTodos() {

        return ResponseEntity.ok(agendamentoService.listarTodos());
    }

    // 2. NOVAS ROTAS: Deixando o seu CRUD completo igual ao do colega

    // Buscar agendamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(agendamentoService.buscarPorId(id));
    }

    // Atualizar/Remarcar agendamento
    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid AgendamentoRequestDTO dto) {

        return ResponseEntity.ok(agendamentoService.atualizar(id, dto));
    }

    // Cancelar/Deletar agendamento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}