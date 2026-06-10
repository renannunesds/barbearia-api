package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.exception.ResourceNotFoundException;
import br.ifg.urt.barbearia_api.model.Especialidade;
import br.ifg.urt.barbearia_api.repository.EspecialidadeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

    // 1. SALVAR - LIMPA O CACHE DA LISTAGEM (Slide 27)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "especialidadesCache", allEntries = true)
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

    // 2. LISTAR PAGINADO - SALVA EM CACHE (Slide 22)
    // A chave do cache muda se o usuário buscar por um nome específico ou mudar a página
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable(value = "especialidadesCache", key = "(#nome == null ? '' : #nome) + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
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

        System.out.println("### CONSULTANDO ESPECIALIDADES NO BANCO DE DADOS... ###");
        Page<Especialidade> resultado;

        if (nome != null && !nome.isBlank()) {
            resultado = repository.findByNomeContainingIgnoreCase(nome, pageable);
        } else {
            resultado = repository.findAll(pageable);
        }

        return ResponseEntity.ok(resultado);
    }

    // 3. BUSCAR POR ID - SALVA EM CACHE INDIVIDUAL (Slide 17)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable(value = "especialidadeIndividualCache", key = "#id")
    @Operation(summary = "Buscar especialidade por ID", description = "Retorna os dados completos de uma única especialidade.")
    public ResponseEntity<Especialidade> findById(@PathVariable Long id) {
        System.out.println("### CONSULTANDO ID NO BANCO DE DADOS... ###");
        Especialidade especialidade = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidade não encontrada com o ID: " + id));
        return ResponseEntity.ok(especialidade);
    }

    // 4. ATUALIZAR - LIMPA AMBOS OS CACHES (Slide 33)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Caching(evict = {
            @CacheEvict(value = "especialidadeIndividualCache", key = "#id"),
            @CacheEvict(value = "especialidadesCache", allEntries = true)
    })
    @Operation(summary = "Atualizar uma especialidade", description = "Modifica os dados de uma especialidade existente.")
    public ResponseEntity<Especialidade> update(@PathVariable Long id, @RequestBody Especialidade dadosNovos) {
        Especialidade existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidade não encontrada com o ID: " + id));

        existente.setNome(dadosNovos.getNome());
        existente.setPreco(dadosNovos.getPreco());

        return ResponseEntity.ok(repository.save(existente));
    }

    // 5. DELETAR - LIMPA AMBOS OS CACHES (Slide 37)
    @DeleteMapping("/{id}")
    @Caching(evict = {
            @CacheEvict(value = "especialidadeIndividualCache", key = "#id"),
            @CacheEvict(value = "especialidadesCache", allEntries = true)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar uma especialidade", description = "Remove uma especialidade permanentemente do sistema.")
    public void delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Especialidade não encontrada com o ID: " + id);
        }
        repository.deleteById(id);
    }
}