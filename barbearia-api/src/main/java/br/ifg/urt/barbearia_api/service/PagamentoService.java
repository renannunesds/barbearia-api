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

    public Pagamento processarPagamento(Pagamento pagamento) {

        Agendamento agendamento = agendamentoRepository.findById(pagamento.getAgendamento().getIdAgendamento())
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado para este pagamento"));

        agendamento.setStatus("CONCLUIDO");
        agendamentoRepository.save(agendamento);

        return pagamentoRepository.save(pagamento);
    }
}