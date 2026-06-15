package br.ifg.urt.barbearia_api.dto.response;

import br.ifg.urt.barbearia_api.model.StatusPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Representação de saída dos dados de um pagamento")
public record PagamentoResponseDTO(
        @Schema(description = "Identificador único do pagamento", example = "1")
        Long idPagamento,

        @Schema(description = "Valor total processado", example = "55.00")
        BigDecimal valorTotal,

        @Schema(description = "Data em que o pagamento foi realizado", example = "2026-06-15")
        LocalDate dataPagamento,

        @Schema(description = "Forma de pagamento utilizada pelo cliente", example = "PIX")
        String formaPagamento,

        @Schema(description = "Status atual do processamento do pagamento", example = "PAGO")
        StatusPagamento status, // Agora é Enum

        @Schema(description = "ID do agendamento ao qual este pagamento pertence", example = "10")
        Long idAgendamento
) {}