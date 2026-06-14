package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.assembler.ItemVendaModelAssembler;
import br.ifg.urt.barbearia_api.dto.request.ItemVendaRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ItemVendaResponseDTO;
import br.ifg.urt.barbearia_api.service.ItemVendaService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/itens-venda")
public class ItemVendaController {

    private final ItemVendaService itemVendaService;
    private final ItemVendaModelAssembler assembler; // Adicionado

    public ItemVendaController(ItemVendaService itemVendaService, ItemVendaModelAssembler assembler) {
        this.itemVendaService = itemVendaService;
        this.assembler = assembler;
    }

    @PostMapping
    public ItemVendaResponseDTO criar(@RequestBody ItemVendaRequestDTO dto) {
        return itemVendaService.criar(dto);
    }

    @GetMapping
    public CollectionModel<EntityModel<ItemVendaResponseDTO>> listar() { // Atualizado para HATEOAS
        List<EntityModel<ItemVendaResponseDTO>> itens = itemVendaService.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(itens,
                linkTo(methodOn(ItemVendaController.class).listar()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ItemVendaResponseDTO>> buscarPorId(@PathVariable Long id) { // Atualizado para HATEOAS
        ItemVendaResponseDTO dto = itemVendaService.buscarPorId(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemVendaResponseDTO> atualizar(@PathVariable Long id, @RequestBody ItemVendaRequestDTO dto) {
        ItemVendaResponseDTO atualizado = itemVendaService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        itemVendaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}