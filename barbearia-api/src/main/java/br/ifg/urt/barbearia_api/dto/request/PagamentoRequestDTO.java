package br.ifg.urt.barbearia_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoRequestDTO(
        @NotNull(message = "O valor total é obrigatório")
        BigDecimal valorTotal,

        @NotNull(message = "A data do pagamento é obrigatória")
        LocalDate dataPagamento,

        @NotBlank(message = "A forma de pagamento é obrigatória")
        String formaPagamento,

        @NotBlank(message = "O status do pagamento é obrigatório")
        String status,

        @NotNull(message = "O ID do agendamento é obrigatório")
        Long idAgendamento
) {}