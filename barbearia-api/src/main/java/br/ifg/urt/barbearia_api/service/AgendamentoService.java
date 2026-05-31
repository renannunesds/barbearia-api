package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.AgendamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.AgendamentoResponseDTO;
import br.ifg.urt.barbearia_api.mapper.AgendamentoMapper;
import br.ifg.urt.barbearia_api.model.Agendamento;
import br.ifg.urt.barbearia_api.repository.AgendamentoRepository;
import br.ifg.urt.barbearia_api.repository.BarbeiroRepository;
import br.ifg.urt.barbearia_api.repository.ClienteRepository;
import br.ifg.urt.barbearia_api.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private AgendamentoMapper agendamentoMapper; // Injetando o seu mapper manual seguro

    public AgendamentoResponseDTO criarAgendamento(AgendamentoRequestDTO dto) {
        // 1. Busca as entidades pelos IDs enviados no record DTO
        var barbeiro = barbeiroRepository.findById(dto.idBarbeiro())
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));

        var cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        var servico = servicoRepository.findById(dto.idServico())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        boolean barbeiroOcupado = agendamentoRepository.existsByBarbeiroAndDataAndHorario(
                barbeiro,
                dto.data(),
                dto.horario()
        );

        if (barbeiroOcupado) {
            throw new RuntimeException("Este barbeiro já possui um agendamento neste horário!");
        }

        // 3. Converte o DTO para Entidade e amarra os relacionamentos
        Agendamento agendamento = agendamentoMapper.requestToEntity(dto);
        agendamento.setBarbeiro(barbeiro);
        agendamento.setCliente(cliente);
        agendamento.setServico(servico);
        agendamento.setStatus("PENDENTE");

        // 4. Salva e retorna o ResponseDTO limpo
        Agendamento salvo = agendamentoRepository.save(agendamento);
        return agendamentoMapper.entityToResponse(salvo);
    }

    public List<AgendamentoResponseDTO> listarTodos() {
        return agendamentoRepository.findAll().stream()
                .map(agendamentoMapper::entityToResponse)
                .collect(Collectors.toList());
    }
}