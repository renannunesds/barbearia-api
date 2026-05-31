package br.ifg.urt.barbearia_api.dto.response;

public record UsuarioResponseDTO(

        Long id,
        String nome,
        String email,
        String telefone

) {
}