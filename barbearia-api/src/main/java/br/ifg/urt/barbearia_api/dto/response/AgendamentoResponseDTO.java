package br.ifg.urt.barbearia_api.dto.response;

import br.ifg.urt.barbearia_api.model.StatusAgendamento;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalTime;

@Schema(description = "Representação de saída dos dados de um agendamento")
public record AgendamentoResponseDTO(

        @Schema(description = "Identificador único do agendamento", example = "1")
        Long idAgendamento,

        @Schema(description = "Data agendada para o serviço", example = "2026-06-15")
        LocalDate data,

        @Schema(description = "Horário agendado para o serviço", example = "14:30")
        LocalTime horario,

        @Schema(description = "Status atual do agendamento", example = "AGENDADO")
        StatusAgendamento status,

        @Schema(description = "Nome do cliente que realizou o agendamento", example = "João Silva")
        String nomeCliente,

        @Schema(description = "Nome do barbeiro responsável pelo atendimento", example = "Marcos")
        String nomeBarbeiro,

        @Schema(description = "Nome do serviço a ser executado", example = "Corte Degradê")
        String nomeServico
) {}