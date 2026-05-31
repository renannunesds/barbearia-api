package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.dto.request.UsuarioRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.UsuarioResponseDTO;
import br.ifg.urt.barbearia_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Validated
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    // Buscar todos
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(service.findAll());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // Criar usuário
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criar(
            @Valid @RequestBody UsuarioRequestDTO dto) {

        UsuarioResponseDTO novoUsuario = service.create(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novoUsuario);
    }

    // Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto) {

        return ResponseEntity.ok(service.update(id, dto));
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}