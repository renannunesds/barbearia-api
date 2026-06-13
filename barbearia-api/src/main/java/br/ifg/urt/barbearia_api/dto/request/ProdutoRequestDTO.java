package br.ifg.urt.barbearia_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProdutoRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String nome,

        String descricao,

        @NotNull(message = "O valor é obrigatório")
        @PositiveOrZero(message = "O valor deve ser maior ou igual a zero")
        BigDecimal valor,

        @NotNull(message = "A quantidade em estoque é obrigatória")
        @PositiveOrZero(message = "A quantidade em estoque deve ser maior ou igual a zero")
        Integer quantidadeEstoque
) {
}