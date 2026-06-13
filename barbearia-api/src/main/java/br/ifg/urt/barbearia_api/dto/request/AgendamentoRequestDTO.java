package br.ifg.urt.barbearia_api.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record AgendamentoRequestDTO(

        @NotNull(message = "A data do agendamento é obrigatória")
        @FutureOrPresent(message = "A data do agendamento não pode ser uma data passada")
        LocalDate data,

        @NotNull(message = "O horário do agendamento é obrigatório")
        LocalTime horario,

        @NotNull(message = "O ID do cliente é obrigatório")
        Long idCliente,

        @NotNull(message = "O ID do barbeiro é obrigatório")
        Long idBarbeiro,

        @NotNull(message = "O ID do serviço é obrigatório")
        Long idServico

) {
}