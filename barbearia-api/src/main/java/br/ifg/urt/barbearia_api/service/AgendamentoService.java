package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.AgendamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.AgendamentoResponseDTO;
import br.ifg.urt.barbearia_api.mapper.AgendamentoMapper;
import br.ifg.urt.barbearia_api.model.Agendamento;
import br.ifg.urt.barbearia_api.model.Barbeiro;
import br.ifg.urt.barbearia_api.model.Cliente;
import br.ifg.urt.barbearia_api.model.Servico;
import br.ifg.urt.barbearia_api.repository.AgendamentoRepository;
import br.ifg.urt.barbearia_api.repository.BarbeiroRepository;
import br.ifg.urt.barbearia_api.repository.ClienteRepository;
import br.ifg.urt.barbearia_api.repository.ServicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final BarbeiroRepository barbeiroRepository;
    private final ClienteRepository clienteRepository;
    private final ServicoRepository servicoRepository;
    private final AgendamentoMapper agendamentoMapper;

    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                              BarbeiroRepository barbeiroRepository,
                              ClienteRepository clienteRepository,
                              ServicoRepository servicoRepository,
                              AgendamentoMapper agendamentoMapper) {
        this.agendamentoRepository = agendamentoRepository;
        this.barbeiroRepository = barbeiroRepository;
        this.clienteRepository = clienteRepository;
        this.servicoRepository = servicoRepository;
        this.agendamentoMapper = agendamentoMapper;
    }

    // 1. CRIAR AGENDAMENTO
    public AgendamentoResponseDTO criarAgendamento(AgendamentoRequestDTO dto) {
        Barbeiro barbeiro = barbeiroRepository.findById(dto.idBarbeiro())
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));

        Cliente cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Servico servico = servicoRepository.findById(dto.idServico())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        boolean barbeiroOcupado = agendamentoRepository.existsByBarbeiroAndDataAndHorario(
                barbeiro,
                dto.data(),
                dto.horario()
        );

        if (barbeiroOcupado) {
            throw new RuntimeException("Este barbeiro já possui um agendamento neste horário!");
        }

        Agendamento agendamento = agendamentoMapper.requestToEntity(dto);
        agendamento.setBarbeiro(barbeiro);
        agendamento.setCliente(cliente);
        agendamento.setServico(servico);
        agendamento.setStatus("PENDENTE");

        Agendamento salvo = agendamentoRepository.save(agendamento);
        return agendamentoMapper.entityToResponse(salvo);
    }

    // 2. LISTAR TODOS
    public Page<AgendamentoResponseDTO> listarTodos(Pageable pageable) {
        return agendamentoRepository.findAll(pageable)
                .map(agendamentoMapper::entityToResponse);
    }

    // 3. BUSCAR POR ID
    public AgendamentoResponseDTO buscarPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento de ID " + id + " não encontrado!"));
        return agendamentoMapper.entityToResponse(agendamento);
    }

    // 4. DELETAR
    public void deletar(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento de ID " + id + " não encontrado!"));
        agendamentoRepository.delete(agendamento);
    }

    // 5. ATUALIZAR
    public AgendamentoResponseDTO atualizar(Long id, AgendamentoRequestDTO dto) {
        Agendamento agendamentoExistente = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento de ID " + id + " não encontrado!"));

        if (!agendamentoExistente.getHorario().equals(dto.horario()) || !agendamentoExistente.getData().equals(dto.data())) {
            boolean horarioOcupado = agendamentoRepository.existsByBarbeiroAndDataAndHorario(
                    agendamentoExistente.getBarbeiro(), dto.data(), dto.horario()
            );
            if (horarioOcupado) {
                throw new RuntimeException("Este barbeiro já possui um agendamento neste novo horário!");
            }
        }

        agendamentoExistente.setData(dto.data());
        agendamentoExistente.setHorario(dto.horario());

        Agendamento agendamentoAtualizado = agendamentoRepository.save(agendamentoExistente);
        return agendamentoMapper.entityToResponse(agendamentoAtualizado);
    }
}