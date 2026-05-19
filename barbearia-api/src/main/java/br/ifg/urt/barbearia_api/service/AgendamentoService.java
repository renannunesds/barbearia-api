package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.model.Agendamento;
import br.ifg.urt.barbearia_api.repository.AgendamentoRepository;
import br.ifg.urt.barbearia_api.repository.BarbeiroRepository;
import br.ifg.urt.barbearia_api.repository.ClienteRepository;
import br.ifg.urt.barbearia_api.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private BarbeiroRepository barbeiroRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    // Código limpo recebendo a entidade direto, sem precisar de Mapper ou DTOs que não existem
    public Agendamento criarAgendamento(Agendamento agendamento) {

        // 1. Valida se o barbeiro já tem agendamento na mesma data e horário
        boolean barbeiroOcupado = agendamentoRepository.existsByBarbeiroAndDataAndHorario(
                agendamento.getBarbeiro(),
                agendamento.getData(),
                agendamento.getHorario()
        );

        if (barbeiroOcupado) {
            throw new RuntimeException("Este barbeiro já possui um agendamento neste horário!");
        }

        // 2. Define o status padrão
        agendamento.setStatus("PENDENTE");

        // 3. Salva direto a entidade no banco
        return agendamentoRepository.save(agendamento);
    }

    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }
}