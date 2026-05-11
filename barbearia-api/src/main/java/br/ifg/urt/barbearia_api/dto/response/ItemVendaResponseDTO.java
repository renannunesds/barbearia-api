package br.ifg.urt.barbearia_api.dto.response;

import java.math.BigDecimal;

public record ItemVendaResponseDTO(
        Long idItemVenda,
        Long idItem,
        String nomeItem,
        Integer quantidade,
        BigDecimal valorUnitario,
        BigDecimal subtotal
) {
}
