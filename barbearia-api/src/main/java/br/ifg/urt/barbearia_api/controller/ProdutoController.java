package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.assembler.ProdutoModelAssembler;
import br.ifg.urt.barbearia_api.dto.request.ProdutoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ProdutoResponseDTO;
import br.ifg.urt.barbearia_api.service.ProdutoService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoModelAssembler assembler; // Adicionado

    public ProdutoController(ProdutoService produtoService, ProdutoModelAssembler assembler) {
        this.produtoService = produtoService;
        this.assembler = assembler;
    }

    @PostMapping
    public ProdutoResponseDTO criar(@RequestBody ProdutoRequestDTO dto) {
        return produtoService.criar(dto);
    }

    @GetMapping
    public CollectionModel<EntityModel<ProdutoResponseDTO>> listar() { // Atualizado para HATEOAS
        List<EntityModel<ProdutoResponseDTO>> produtos = produtoService.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(produtos,
                linkTo(methodOn(ProdutoController.class).listar()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) { // Atualizado para HATEOAS
        ProdutoResponseDTO dto = produtoService.buscarPorId(id);
        return assembler.toModel(dto);
    }

    @PutMapping("/{id}")
    public ProdutoResponseDTO atualizar(@PathVariable Long id, @RequestBody ProdutoRequestDTO dto) {
        return produtoService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        produtoService.deletar(id);
    }
}