//package br.ifg.urt.barbearia_api.controller;
//
//import br.ifg.urt.barbearia_api.dto.request.AgendamentoRequestDTO;
//import br.ifg.urt.barbearia_api.dto.response.AgendamentoResponseDTO;
//import br.ifg.urt.barbearia_api.service.AgendamentoService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/agendamentos")
//public class AgendamentoController {
//
//    @Autowired
//    private AgendamentoService service;
//
//    @PostMapping
//    public ResponseEntity<AgendamentoResponseDTO> criar(@RequestBody @Valid AgendamentoRequestDTO dto) {
//        return ResponseEntity.ok(service.salvar(dto));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<AgendamentoResponseDTO>> listar() {
//        return ResponseEntity.ok(service.listarTodos());
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> excluir(@PathVariable Long id) {
//        service.deletar(id);
//        return ResponseEntity.noContent().build();
//    }
//}