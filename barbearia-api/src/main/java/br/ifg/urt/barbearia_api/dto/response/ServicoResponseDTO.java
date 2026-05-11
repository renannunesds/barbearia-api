package br.ifg.urt.barbearia_api.dto.response;

import java.math.BigDecimal;

public record ServicoResponseDTO(
        Long idItem,
        String nome,
        String descricao,
        BigDecimal valor,
        Integer duracaoMinutos
) {
}
