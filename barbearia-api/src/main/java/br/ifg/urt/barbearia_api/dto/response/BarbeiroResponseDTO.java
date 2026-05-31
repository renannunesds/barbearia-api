package br.ifg.urt.barbearia_api.dto.response;

public record BarbeiroResponseDTO(

        Long id,
        String nome,
        String email,
        String telefone,
        String especialidade,
        Boolean ativo

) {
}