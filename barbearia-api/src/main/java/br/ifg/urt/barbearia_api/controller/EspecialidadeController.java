package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.model.Especialidade;
import br.ifg.urt.barbearia_api.repository.EspecialidadeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especialidades")
@Tag(name = "Especialidades", description = "Endpoints para gerenciamento das especialidades e serviços da barbearia")
public class EspecialidadeController {

    private final EspecialidadeRepository repository;

    public EspecialidadeController(EspecialidadeRepository repository) {
        this.repository = repository;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Criar nova especialidade",
            description = "Cadastra um novo tipo de serviço oferecido na barbearia (ex: Corte, Barba).",
            responses = {
                    @ApiResponse(description = "Criado com sucesso", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = Especialidade.class))),
                    @ApiResponse(description = "Erro na requisição", responseCode = "400", content = @Content)
            }
    )
    public ResponseEntity<Especialidade> create(@RequestBody Especialidade especialidade) {
        Especialidade salva = repository.save(especialidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar todas as especialidades",
            description = "Retorna todos os registros de especialidades salvas no banco de dados.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Especialidade.class)))),
                    @ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<List<Especialidade>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }
}