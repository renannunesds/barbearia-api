package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.PagamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.PagamentoResponseDTO;
import br.ifg.urt.barbearia_api.mapper.PagamentoMapper;
import br.ifg.urt.barbearia_api.model.Pagamento;
import br.ifg.urt.barbearia_api.repository.AgendamentoRepository;
import br.ifg.urt.barbearia_api.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private PagamentoMapper mapper;

    public PagamentoResponseDTO salvar(PagamentoRequestDTO dto) {
        // 1. Busca o Agendamento que está sendo pago
        Agendamento agendamento = agendamentoRepository.findById(dto.idAgendamento())
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado para este pagamento"));

        // 2. Converte DTO para Entidade
        Pagamento pagamento = mapper.toEntity(dto);

        // 3. Vincula o pagamento ao agendamento
        pagamento.setAgendamento(agendamento);

        // 4. Salva no banco
        pagamento = repository.save(pagamento);

        // 5. Retorna a resposta
        return mapper.toResponseDTO(pagamento);
    }

    public List<PagamentoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}