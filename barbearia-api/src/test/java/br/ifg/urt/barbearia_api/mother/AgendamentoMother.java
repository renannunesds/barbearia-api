package br.ifg.urt.barbearia_api.mother;

import br.ifg.urt.barbearia_api.dto.request.AgendamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.AgendamentoResponseDTO;
import br.ifg.urt.barbearia_api.model.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class AgendamentoMother {

    public static Agendamento padrao() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");

        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setNome("Marcos");

        Servico servico = new Servico();
        servico.setNome("Corte Degradê");

        return new Agendamento(
                1L,
                LocalDate.now().plusDays(1),
                LocalTime.of(14, 30),
                "PENDENTE",
                cliente,
                barbeiro,
                servico,
                null
        );
    }

    public static AgendamentoRequestDTO requestValido() {
        return new AgendamentoRequestDTO(
                LocalDate.now().plusDays(1),
                LocalTime.of(14, 30),
                1L,
                1L,
                1L
        );
    }

    public static AgendamentoResponseDTO responseValido() {
        return new AgendamentoResponseDTO(
                1L,
                LocalDate.now().plusDays(1),
                LocalTime.of(14, 30),
                "PENDENTE",
                "João Silva",
                "Marcos",
                "Corte Degradê"
        );
    }
}