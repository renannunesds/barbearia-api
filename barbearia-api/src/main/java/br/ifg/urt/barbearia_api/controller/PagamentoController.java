package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.assembler.PagamentoModelAssembler;
import br.ifg.urt.barbearia_api.dto.request.PagamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.PagamentoResponseDTO;
import br.ifg.urt.barbearia_api.service.PagamentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
@Validated
@Tag(name = "Pagamentos", description = "Endpoints para pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;
    private final PagamentoModelAssembler assembler;

    public PagamentoController(PagamentoService pagamentoService, PagamentoModelAssembler assembler) {
        this.pagamentoService = pagamentoService;
        this.assembler = assembler;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "pagamentosCache", allEntries = true)
    public ResponseEntity<EntityModel<PagamentoResponseDTO>> processar(@RequestBody @Valid PagamentoRequestDTO dto) {
        PagamentoResponseDTO response = pagamentoService.processarPagamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(response));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable(value = "pagamentosCache", key = "{#pageable.pageNumber, #pageable.pageSize}")
    public ResponseEntity<PagedModel<EntityModel<PagamentoResponseDTO>>> listarTodos(
            @ParameterObject @PageableDefault(size = 10, sort = "dataPagamento") Pageable pageable,
            PagedResourcesAssembler<PagamentoResponseDTO> pagedResourcesAssembler) {

        Page<PagamentoResponseDTO> page = pagamentoService.listarTodos(pageable);

        return ResponseEntity.ok(pagedResourcesAssembler.toModel(page, (RepresentationModelAssembler) assembler));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<PagamentoResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(pagamentoService.buscarPorId(id)));
    }
}