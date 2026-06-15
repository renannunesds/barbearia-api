package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.PagamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.PagamentoResponseDTO;
import br.ifg.urt.barbearia_api.exception.PagamentoRecusadoException;
import br.ifg.urt.barbearia_api.mapper.PagamentoMapper;
import br.ifg.urt.barbearia_api.model.Agendamento;
import br.ifg.urt.barbearia_api.model.Pagamento;
import br.ifg.urt.barbearia_api.model.StatusAgendamento; // Import necessário
import br.ifg.urt.barbearia_api.repository.AgendamentoRepository;
import br.ifg.urt.barbearia_api.repository.PagamentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public PagamentoResponseDTO processarPagamento(PagamentoRequestDTO dto) {
        Agendamento agendamento = agendamentoRepository.findByIdOrThrow(dto.idAgendamento());

        if (StatusAgendamento.CANCELADO.equals(agendamento.getStatus())) {
            throw new PagamentoRecusadoException("Não é possível processar o pagamento de um agendamento cancelado!");
        }

        agendamento.setStatus(StatusAgendamento.CONCLUIDO);
        agendamentoRepository.save(agendamento);

        Pagamento pagamento = pagamentoMapper.requestToEntity(dto);
        pagamento.setAgendamento(agendamento);
        pagamento.confirmarPagamento();

        Pagamento salvo = pagamentoRepository.save(pagamento);
        return pagamentoMapper.entityToResponse(salvo);
    }

    public Page<PagamentoResponseDTO> listarTodos(Pageable pageable) {
        return pagamentoRepository.findAll(pageable)
                .map(pagamentoMapper::entityToResponse);
    }

    public PagamentoResponseDTO buscarPorId(Long id) {
        Pagamento pagamento = pagamentoRepository.findByIdOrThrow(id);
        return pagamentoMapper.entityToResponse(pagamento);
    }

    @Transactional
    public void estornarPagamento(Long id) {
        Pagamento pagamento = pagamentoRepository.findByIdOrThrow(id);
        pagamento.estornarPagamento();
        pagamentoRepository.save(pagamento);
    }
}