package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.AgendamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.AgendamentoResponseDTO;
import br.ifg.urt.barbearia_api.exception.AgendamentoInvalidoException;
import br.ifg.urt.barbearia_api.mapper.AgendamentoMapper;
import br.ifg.urt.barbearia_api.model.Agendamento;
import br.ifg.urt.barbearia_api.model.Barbeiro;
import br.ifg.urt.barbearia_api.model.Cliente;
import br.ifg.urt.barbearia_api.model.Servico;
import br.ifg.urt.barbearia_api.model.StatusAgendamento; // Import do Enum
import br.ifg.urt.barbearia_api.repository.AgendamentoRepository;
import br.ifg.urt.barbearia_api.repository.BarbeiroRepository;
import br.ifg.urt.barbearia_api.repository.ClienteRepository;
import br.ifg.urt.barbearia_api.repository.ServicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public AgendamentoResponseDTO criarAgendamento(AgendamentoRequestDTO dto) {
        Barbeiro barbeiro = barbeiroRepository.findByIdOrThrow(dto.idBarbeiro());
        Cliente cliente = clienteRepository.findByIdOrThrow(dto.idCliente());
        Servico servico = servicoRepository.findByIdOrThrow(dto.idServico());

        boolean barbeiroOcupado = agendamentoRepository.existsByBarbeiroAndDataAndHorario(
                barbeiro, dto.data(), dto.horario());

        if (barbeiroOcupado) {
            throw new AgendamentoInvalidoException("Este barbeiro já possui um agendamento neste horário!");
        }

        Agendamento agendamento = agendamentoMapper.requestToEntity(dto);
        agendamento.setBarbeiro(barbeiro);
        agendamento.setCliente(cliente);
        agendamento.setServico(servico);
        // Utilizando o Enum em vez da String
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        Agendamento salvo = agendamentoRepository.save(agendamento);
        return agendamentoMapper.entityToResponse(salvo);
    }

    public Page<AgendamentoResponseDTO> listarTodos(Pageable pageable) {
        return agendamentoRepository.findAll(pageable)
                .map(agendamentoMapper::entityToResponse);
    }

    public AgendamentoResponseDTO buscarPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findByIdOrThrow(id);
        return agendamentoMapper.entityToResponse(agendamento);
    }

    @Transactional
    public void confirmarAgendamento(Long id) {
        Agendamento agendamento = agendamentoRepository.findByIdOrThrow(id);
        agendamento.confirmarAgendamento();
        agendamentoRepository.save(agendamento);
    }

    @Transactional
    public void cancelarAgendamento(Long id) {
        Agendamento agendamento = agendamentoRepository.findByIdOrThrow(id);
        agendamento.cancelarAgendamento();
        agendamentoRepository.save(agendamento);
    }

    @Transactional
    public void deletar(Long id) {
        Agendamento agendamento = agendamentoRepository.findByIdOrThrow(id);
        agendamentoRepository.delete(agendamento);
    }

    @Transactional
    public AgendamentoResponseDTO atualizar(Long id, AgendamentoRequestDTO dto) {
        Agendamento agendamentoExistente = agendamentoRepository.findByIdOrThrow(id);

        if (!agendamentoExistente.getHorario().equals(dto.horario()) || !agendamentoExistente.getData().equals(dto.data())) {
            boolean horarioOcupado = agendamentoRepository.existsByBarbeiroAndDataAndHorario(
                    agendamentoExistente.getBarbeiro(), dto.data(), dto.horario()
            );
            if (horarioOcupado) {
                throw new AgendamentoInvalidoException("Este barbeiro já possui um agendamento neste novo horário!");
            }
        }

        agendamentoExistente.setData(dto.data());
        agendamentoExistente.setHorario(dto.horario());

        Agendamento agendamentoAtualizado = agendamentoRepository.save(agendamentoExistente);
        return agendamentoMapper.entityToResponse(agendamentoAtualizado);
    }
}