package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.AgendamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.AgendamentoResponseDTO;
import br.ifg.urt.barbearia_api.mapper.AgendamentoMapper;
import br.ifg.urt.barbearia_api.model.Barbeiro;
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
    private AgendamentoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BarbeiroRepository barbeiroRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private AgendamentoMapper mapper;

    public AgendamentoResponseDTO salvar(AgendamentoRequestDTO dto) {
        // Busca o Barbeiro
        Barbeiro barbeiro = barbeiroRepository.findById(dto.idBarbeiro())
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));

        // Regra de Negócio: Evitar conflito de horário
        boolean ocupado = repository.existsByBarbeiroAndDataAndHorario(barbeiro, dto.data(), dto.horario());
        if (ocupado) {
            throw new RuntimeException("Este barbeiro já possui um agendamento para este horário!");
        }

        Agendamento agendamento = mapper.toEntity(dto);

        agendamento.setBarbeiro(barbeiro);
        agendamento.setCliente(clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado")));
        agendamento.setServico(servicoRepository.findById(dto.idServico())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado")));

        agendamento = repository.save(agendamento);

        return mapper.toResponseDTO(agendamento);
    }

    public List<AgendamentoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}