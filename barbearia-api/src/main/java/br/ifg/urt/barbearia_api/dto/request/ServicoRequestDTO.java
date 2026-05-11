package br.ifg.urt.barbearia_api.dto.request;

import java.math.BigDecimal;

public record ServicoRequestDTO(
        String nome,
        String descricao,
        BigDecimal valor,
        Integer duracaoMinutos
) {
}

