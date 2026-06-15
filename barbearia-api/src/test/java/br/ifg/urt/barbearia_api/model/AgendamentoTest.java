package br.ifg.urt.barbearia_api.model;

import br.ifg.urt.barbearia_api.mother.AgendamentoMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgendamentoTest {

    @Test
    @DisplayName("Deve alterar o status para AGENDADO ao chamar confirmarAgendamento")
    void deveConfirmarAgendamento() {
        Agendamento agendamento = AgendamentoMother.padrao();

        agendamento.confirmarAgendamento();

        assertEquals(StatusAgendamento.AGENDADO, agendamento.getStatus());
    }

    @Test
    @DisplayName("Deve alterar o status para CANCELADO ao chamar cancelarAgendamento")
    void deveCancelarAgendamento() {
        Agendamento agendamento = AgendamentoMother.padrao();

        agendamento.cancelarAgendamento();

        assertEquals(StatusAgendamento.CANCELADO, agendamento.getStatus());
    }

    @Test
    @DisplayName("Deve vincular o agendamento bidirecionalmente ao setar um pagamento")
    void deveVincularPagamentoCorretamente() {
        Agendamento agendamento = AgendamentoMother.padrao();
        Pagamento pagamento = new Pagamento();

        agendamento.setPagamento(pagamento);

        assertNotNull(agendamento.getPagamento());
        assertEquals(agendamento, pagamento.getAgendamento());
    }
}