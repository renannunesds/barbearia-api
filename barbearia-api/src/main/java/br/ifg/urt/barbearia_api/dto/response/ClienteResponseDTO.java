package br.ifg.urt.barbearia_api.dto.response;

public record ClienteResponseDTO(

        Long id,
        String nome,
        String email,
        String telefone,
        String observacoes

) {
}