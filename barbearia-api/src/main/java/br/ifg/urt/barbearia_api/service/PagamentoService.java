package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.PagamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.PagamentoResponseDTO;
import br.ifg.urt.barbearia_api.mapper.PagamentoMapper;
import br.ifg.urt.barbearia_api.model.Agendamento;
import br.ifg.urt.barbearia_api.model.Pagamento;
import br.ifg.urt.barbearia_api.repository.AgendamentoRepository;
import br.ifg.urt.barbearia_api.repository.PagamentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final PagamentoMapper pagamentoMapper;

    public PagamentoService(PagamentoRepository pagamentoRepository,
                            AgendamentoRepository agendamentoRepository,
                            PagamentoMapper pagamentoMapper) {
        this.pagamentoRepository = pagamentoRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.pagamentoMapper = pagamentoMapper;
    }

    // Regra de negócio: Processa o pagamento e vincula ao agendamento
    public PagamentoResponseDTO processarPagamento(PagamentoRequestDTO dto) {
        // Verifica se o agendamento existe
        Agendamento agendamento = agendamentoRepository.findById(dto.idAgendamento())
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        // Atualiza status do agendamento para concluído após o pagamento
        agendamento.setStatus("CONCLUIDO");
        agendamentoRepository.save(agendamento);

        // Transforma DTO em Entidade, vincula o agendamento e confirma o status
        Pagamento pagamento = pagamentoMapper.requestToEntity(dto);
        pagamento.setAgendamento(agendamento);
        pagamento.confirmarPagamento();

        // Persiste no banco e retorna o DTO de resposta
        Pagamento salvo = pagamentoRepository.save(pagamento);
        return pagamentoMapper.entityToResponse(salvo);
    }

    // Lista paginada: Evita trazer todos os registros
    public Page<PagamentoResponseDTO> listarTodos(Pageable pageable) {
        // O findAll(pageable) retorna uma página de entidades, o map converte para DTO
        return pagamentoRepository.findAll(pageable)
                .map(pagamentoMapper::entityToResponse);
    }

    // Busca detalhada por ID
    public PagamentoResponseDTO buscarPorId(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento de ID " + id + " não encontrado!"));
        return pagamentoMapper.entityToResponse(pagamento);
    }
}