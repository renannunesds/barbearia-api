package br.ifg.urt.barbearia_api.service;

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

    // Código limpo que recebe e processa a entidade direto, sem depender de DTO ou Mapper
    public Pagamento processarPagamento(Pagamento pagamento) {

        // 1. Busca o agendamento associado para garantir que ele existe
        Agendamento agendamento = agendamentoRepository.findById(pagamento.getAgendamento().getIdAgendamento())
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado para este pagamento"));

        // 2. Regra de Negócio: Se o pagamento foi realizado, o status do agendamento vira CONCLUIDO
        agendamento.setStatus("CONCLUIDO");
        agendamentoRepository.save(agendamento);

        // 3. Salva o pagamento direto no banco
        return pagamentoRepository.save(pagamento);
    }
}