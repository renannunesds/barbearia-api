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

    public Agendamento criarAgendamento(Agendamento agendamento) {

        boolean barbeiroOcupado = agendamentoRepository.existsByBarbeiroAndDataAndHorario(
                agendamento.getBarbeiro(),
                agendamento.getData(),
                agendamento.getHorario()
        );

        if (barbeiroOcupado) {
            throw new RuntimeException("Este barbeiro já possui um agendamento neste horário!");
        }

        agendamento.setStatus("PENDENTE");

        return agendamentoRepository.save(agendamento);
    }

    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }
}