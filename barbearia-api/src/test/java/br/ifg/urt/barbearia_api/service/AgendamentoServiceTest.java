package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.AgendamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.AgendamentoResponseDTO;
import br.ifg.urt.barbearia_api.exception.AgendamentoInvalidoException; // <--- Import da sua nova exceção
import br.ifg.urt.barbearia_api.mapper.AgendamentoMapper;
import br.ifg.urt.barbearia_api.model.*;
import br.ifg.urt.barbearia_api.mother.AgendamentoMother;
import br.ifg.urt.barbearia_api.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @Mock private AgendamentoRepository agendamentoRepository;
    @Mock private BarbeiroRepository barbeiroRepository;
    @Mock private ClienteRepository clienteRepository;
    @Mock private ServicoRepository servicoRepository;
    @Mock private AgendamentoMapper agendamentoMapper;

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Test
    @DisplayName("Deve criar agendamento com sucesso quando barbeiro está disponível")
    void deveCriarAgendamentoComSucesso() {
        AgendamentoRequestDTO dto = AgendamentoMother.requestValido();
        Agendamento agendamento = AgendamentoMother.padrao();
        AgendamentoResponseDTO responseDTO = AgendamentoMother.responseValido();

        when(barbeiroRepository.findByIdOrThrow(any())).thenReturn(new Barbeiro());
        when(clienteRepository.findByIdOrThrow(any())).thenReturn(new Cliente());
        when(servicoRepository.findByIdOrThrow(any())).thenReturn(new Servico());
        when(agendamentoRepository.existsByBarbeiroAndDataAndHorario(any(), any(), any())).thenReturn(false);
        when(agendamentoMapper.requestToEntity(any())).thenReturn(agendamento);
        when(agendamentoRepository.save(any())).thenReturn(agendamento);
        when(agendamentoMapper.entityToResponse(any())).thenReturn(responseDTO);

        AgendamentoResponseDTO result = agendamentoService.criarAgendamento(dto);

        assertNotNull(result);
        verify(agendamentoRepository, times(1)).save(any(Agendamento.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando barbeiro estiver ocupado")
    void deveLancarExcecaoQuandoBarbeiroOcupado() {
        AgendamentoRequestDTO dto = AgendamentoMother.requestValido();

        when(barbeiroRepository.findByIdOrThrow(any())).thenReturn(new Barbeiro());
        when(agendamentoRepository.existsByBarbeiroAndDataAndHorario(any(), any(), any())).thenReturn(true);

        // MUDADO AQUI: Agora valida se o método joga a AgendamentoInvalidoException de verdade!
        assertThrows(AgendamentoInvalidoException.class, () -> agendamentoService.criarAgendamento(dto));

        verify(agendamentoRepository, never()).save(any());
    }
}