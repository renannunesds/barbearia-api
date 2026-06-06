package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.dto.request.ItemVendaRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ItemVendaResponseDTO;
import br.ifg.urt.barbearia_api.service.ItemVendaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-venda")
public class ItemVendaController {

    private final ItemVendaService itemVendaService;

    public ItemVendaController(ItemVendaService itemVendaService) {
        this.itemVendaService = itemVendaService;
    }

    @PostMapping
    public ItemVendaResponseDTO criar(@RequestBody ItemVendaRequestDTO dto) {
        return itemVendaService.criar(dto);
    }

    @GetMapping
    public List<ItemVendaResponseDTO> listar() {
        return itemVendaService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemVendaResponseDTO> buscarPorId(@PathVariable Long id) {
        // Altere para findById(id) se o nome no seu Service for em inglês
        ItemVendaResponseDTO dto = itemVendaService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemVendaResponseDTO> atualizar(@PathVariable Long id, @RequestBody ItemVendaRequestDTO dto) {
        // Altere para alterar(id, dto) ou update(id, dto) se o nome no Service for diferente
        ItemVendaResponseDTO atualizado = itemVendaService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        itemVendaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}