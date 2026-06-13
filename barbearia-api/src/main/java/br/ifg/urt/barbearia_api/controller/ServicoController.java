package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.dto.request.ServicoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ServicoResponseDTO;
import br.ifg.urt.barbearia_api.service.ServicoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @PostMapping
    public ServicoResponseDTO criar(@RequestBody ServicoRequestDTO dto) {
        return servicoService.criar(dto);
    }

    @GetMapping
    public Page<ServicoResponseDTO> listar(
            @RequestParam(required = false) String nome,
            Pageable pageable
    ) {
        return servicoService.listar(nome, pageable);
    }

    @GetMapping("/{id}")
    public ServicoResponseDTO buscarPorId(@PathVariable Long id) {
        return servicoService.buscarPorId(id);
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
