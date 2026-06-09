package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.model.Especialidade;
import br.ifg.urt.barbearia_api.repository.EspecialidadeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadeController {

    private final EspecialidadeRepository repository;

    // Injeção direta do repository para ser rápido e simples para o trabalho
    public EspecialidadeController(EspecialidadeRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Especialidade> create(@RequestBody Especialidade especialidade) {
        Especialidade salva = repository.save(especialidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping
    public ResponseEntity<List<Especialidade>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }
}