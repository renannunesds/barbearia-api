package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.dto.request.ItemVendaRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ItemVendaResponseDTO;
import br.ifg.urt.barbearia_api.service.ItemVendaService;
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
    public ItemVendaResponseDTO buscarPorId(@PathVariable Long id) {
        return itemVendaService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ItemVendaResponseDTO atualizar(@PathVariable Long id, @RequestBody ItemVendaRequestDTO dto) {
        return itemVendaService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        itemVendaService.deletar(id);
    }
}