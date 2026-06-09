package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.model.Especialidade;
import br.ifg.urt.barbearia_api.repository.EspecialidadeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Listar todas as especialidades (REFATORADO E CORRIGIDO PARA O SWAGGER)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar especialidades paginadas",
            description = "Retorna uma página contendo os registros de especialidades salvas no banco. Permite filtrar por nome.",
            parameters = {
                    @Parameter(name = "nome", description = "Filtrar por parte do nome da especialidade", required = false),
                    @Parameter(name = "page", description = "Número da página (começa em 0)", schema = @Schema(type = "integer", defaultValue = "0")),
                    @Parameter(name = "size", description = "Quantidade de elementos por página", schema = @Schema(type = "integer", defaultValue = "10")),
                    @Parameter(name = "sort", description = "Ordenação dos resultados (ex: nome,asc)", schema = @Schema(type = "string", defaultValue = "nome,asc"))
            },
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))),
                    @ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<Page<Especialidade>> findAll(
            @RequestParam(required = false) String nome,
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        Page<Especialidade> resultado;

        if (nome != null && !nome.isBlank()) {
            resultado = repository.findByNomeContainingIgnoreCase(nome, pageable);
        } else {
            resultado = repository.findAll(pageable);
        }

        return ResponseEntity.ok(resultado);
    }
}