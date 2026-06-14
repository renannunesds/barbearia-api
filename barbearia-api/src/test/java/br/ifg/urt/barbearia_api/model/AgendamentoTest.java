package br.ifg.urt.barbearia_api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

@DisplayName("Testes do Modelo de Agendamento")
public class AgendamentoTest {

    private Agendamento agendamento;

    @BeforeEach
    void setup() {
        // Inicializa o objeto antes de cada teste
        this.agendamento = new Agendamento();
    }

    @Test
    @DisplayName("Deve instanciar um agendamento e manipular seus atributos corretamente")
    void deveInstanciarAgendamentoComDadosCorretos() {
        // 1. ARRANGE
        Long idEsperado = 1L;
        LocalDate dataEsperada = LocalDate.of(2026, 6, 20);
        LocalTime horarioEsperado = LocalTime.of(14, 30);
        String statusEsperado = "PENDENTE";

        // 2. ACT
        agendamento.setIdAgendamento(idEsperado);
        agendamento.setData(dataEsperada);
        agendamento.setHorario(horarioEsperado);
        agendamento.setStatus(statusEsperado);

        // 3. ASSERT
        assertNotNull(agendamento, "O objeto agendamento não deveria ser nulo");
        assertEquals(idEsperado, agendamento.getIdAgendamento());
        assertEquals(dataEsperada, agendamento.getData());
        assertEquals(horarioEsperado, agendamento.getHorario());
        assertEquals(statusEsperado, agendamento.getStatus());
    }
}