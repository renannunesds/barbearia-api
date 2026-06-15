package br.ifg.urt.barbearia_api.controller;

import br.ifg.urt.barbearia_api.assembler.AgendamentoModelAssembler;
import br.ifg.urt.barbearia_api.dto.request.AgendamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.AgendamentoResponseDTO;
import br.ifg.urt.barbearia_api.exception.AgendamentoInvalidoException; // Import da sua exceção nova
import br.ifg.urt.barbearia_api.mother.AgendamentoMother;
import br.ifg.urt.barbearia_api.service.AgendamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // Padrão do Professor
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AgendamentoController.class)
class AgendamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AgendamentoService agendamentoService;

    @MockitoBean
    private AgendamentoModelAssembler assembler;

    @Test
    @DisplayName("Deve retornar Status 201 ao criar um agendamento com sucesso")
    void deveCriarAgendamentoComSucesso() throws Exception {
        // Arrange
        AgendamentoRequestDTO request = AgendamentoMother.requestValido();
        AgendamentoResponseDTO response = AgendamentoMother.responseValido();

        when(agendamentoService.criarAgendamento(any(AgendamentoRequestDTO.class))).thenReturn(response);
        when(assembler.toModel(any(AgendamentoResponseDTO.class))).thenReturn(EntityModel.of(response));

        // Act & Assert
        mockMvc.perform(post("/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idAgendamento").value(1L))
                .andExpect(jsonPath("$.status").value("PENDENTE"));
    }

    @Test
    @DisplayName("Deve retornar Status 200 ao buscar um agendamento por ID")
    void deveBuscarPorIdComSucesso() throws Exception {
        // Arrange
        AgendamentoResponseDTO response = AgendamentoMother.responseValido();

        when(agendamentoService.buscarPorId(1L)).thenReturn(response);
        when(assembler.toModel(any(AgendamentoResponseDTO.class))).thenReturn(EntityModel.of(response));

        // Act & Assert
        mockMvc.perform(get("/agendamentos/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAgendamento").value(1L))
                .andExpect(jsonPath("$.status").value("PENDENTE"));
    }

    @Test
    @DisplayName("Deve retornar Status 400 Bad Request ao tentar agendar horário ocupado")
    void deveRetornar400QuandoHorarioOcupado() throws Exception {
        // Arrange
        AgendamentoRequestDTO request = AgendamentoMother.requestValido();

        // Força o Service mockado a disparar o erro customizado que você criou
        when(agendamentoService.criarAgendamento(any(AgendamentoRequestDTO.class)))
                .thenThrow(new AgendamentoInvalidoException("Este barbeiro já possui um agendamento neste horário!"));

        // Act & Assert
        mockMvc.perform(post("/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()); // Garante que a API retorna o HTTP 400 certinho
    }
}