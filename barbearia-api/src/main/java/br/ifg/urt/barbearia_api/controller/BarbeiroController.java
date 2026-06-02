package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.dto.request.BarbeiroRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.BarbeiroResponseDTO;
import br.ifg.urt.barbearia_api.service.BarbeiroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barbeiros")
@Validated
public class BarbeiroController {

    private final BarbeiroService service;

    public BarbeiroController(BarbeiroService service) {
        this.service = service;
    }

    // Buscar todos os barbeiros
    @GetMapping
    public ResponseEntity<List<BarbeiroResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(service.findAll());
    }

    // Buscar barbeiro por ID
    @GetMapping("/{id}")
    public ResponseEntity<BarbeiroResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // Criar um novo barbeiro
    @PostMapping
    public ResponseEntity<BarbeiroResponseDTO> criar(@Valid @RequestBody BarbeiroRequestDTO dto) {
        BarbeiroResponseDTO novoBarbeiro = service.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novoBarbeiro);
    }

    // Atualizar os dados de um barbeiro
    @PutMapping("/{id}")
    public ResponseEntity<BarbeiroResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody BarbeiroRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // Ativar o status de um barbeiro
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativarBarbeiro(@PathVariable Long id) {
        service.ativarBarbeiro(id);
        return ResponseEntity.noContent().build();
    }

    // Desativar o status de um barbeiro
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativarBarbeiro(@PathVariable Long id) {
        service.desativarBarbeiro(id);
        return ResponseEntity.noContent().build();
    }

    // Deletar logicamente ou fisicamente um barbeiro do sistema
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}