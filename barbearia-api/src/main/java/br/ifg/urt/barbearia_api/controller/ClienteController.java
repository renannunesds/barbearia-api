package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.dto.request.ClienteRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ClienteResponseDTO;
import br.ifg.urt.barbearia_api.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@Validated
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    // Buscar todos
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> buscarTodos() {

        return ResponseEntity.ok(service.findAll());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.findById(id));
    }

    // Criar cliente
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criar(
            @Valid @RequestBody ClienteRequestDTO dto) {

        ClienteResponseDTO novoCliente = service.create(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novoCliente);
    }

    // Atualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO dto) {

        return ResponseEntity.ok(service.update(id, dto));
    }

    // Deletar cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}