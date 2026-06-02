package br.ifg.urt.barbearia_api.dto.response;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String telefoneFormatado, // Novo campo extraído da lógica do VO
        String observacoes
) {
}
