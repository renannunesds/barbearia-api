package br.ifg.urt.barbearia_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoResponseDTO(
        Long idPagamento,
        BigDecimal valorTotal,
        LocalDate dataPagamento,
        String formaPagamento,
        String status,
        Long idAgendamento
) {}