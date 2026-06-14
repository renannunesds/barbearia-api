package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.assembler.ServicoModelAssembler;
import br.ifg.urt.barbearia_api.dto.request.ServicoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ServicoResponseDTO;
import br.ifg.urt.barbearia_api.service.ServicoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler; // Adicionado
import org.springframework.hateoas.EntityModel; // Adicionado
import org.springframework.hateoas.PagedModel; // Adicionado
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;
    private final ServicoModelAssembler assembler; // Adicionado
    private final PagedResourcesAssembler<ServicoResponseDTO> pagedResourcesAssembler; // Adicionado

    public ServicoController(ServicoService servicoService,
                             ServicoModelAssembler assembler,
                             PagedResourcesAssembler<ServicoResponseDTO> pagedResourcesAssembler) {
        this.servicoService = servicoService;
        this.assembler = assembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @PostMapping
    public ServicoResponseDTO criar(@RequestBody ServicoRequestDTO dto) {
        return servicoService.criar(dto);
    }

    @GetMapping
    public PagedModel<EntityModel<ServicoResponseDTO>> listar( // Atualizado para Paged HATEOAS
                                                               @RequestParam(required = false) String nome,
                                                               Pageable pageable
    ) {
        Page<ServicoResponseDTO> page = servicoService.listar(nome, pageable);
        return pagedResourcesAssembler.toModel(page, assembler);
    }

    @GetMapping("/{id}")
    public EntityModel<ServicoResponseDTO> buscarPorId(@PathVariable Long id) { // Atualizado para HATEOAS
        ServicoResponseDTO dto = servicoService.buscarPorId(id);
        return assembler.toModel(dto);
    }

    @PutMapping("/{id}")
    public ServicoResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody ServicoRequestDTO dto
    ) {
        return servicoService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        servicoService.deletar(id);
    }
}