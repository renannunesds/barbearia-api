package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.PagamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.PagamentoResponseDTO;
import br.ifg.urt.barbearia_api.mapper.PagamentoMapper;
import br.ifg.urt.barbearia_api.model.Agendamento;
import br.ifg.urt.barbearia_api.model.Pagamento;
import br.ifg.urt.barbearia_api.repository.AgendamentoRepository;
import br.ifg.urt.barbearia_api.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private PagamentoMapper pagamentoMapper; // Injetando o seu mapper manual seguro

    public PagamentoResponseDTO processarPagamento(PagamentoRequestDTO dto) {
        // 1. Busca o agendamento pelo ID enviado no record DTO (usando dto.idAgendamento())
        Agendamento agendamento = agendamentoRepository.findById(dto.idAgendamento())
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado para este pagamento"));

        // 2. Atualiza o status do agendamento para concluído
        agendamento.setStatus("CONCLUIDO");
        agendamentoRepository.save(agendamento);

        // 3. Converte o DTO para a Entidade Pagamento e vincula o agendamento encontrado
        Pagamento pagamento = pagamentoMapper.requestToEntity(dto);
        pagamento.setAgendamento(agendamento);

        // 4. Salva o pagamento e retorna o ResponseDTO limpo
        Pagamento salvo = pagamentoRepository.save(pagamento);
        return pagamentoMapper.entityToResponse(salvo);
    }
}