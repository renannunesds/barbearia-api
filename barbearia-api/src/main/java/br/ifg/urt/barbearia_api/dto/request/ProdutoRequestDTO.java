package br.ifg.urt.barbearia_api.dto.request;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        String nome,
        String descricao,
        BigDecimal valor,
        Integer quantidadeEstoque )
{
}

