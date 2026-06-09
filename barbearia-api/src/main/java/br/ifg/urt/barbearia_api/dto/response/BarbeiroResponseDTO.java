package br.ifg.urt.barbearia_api.dto.response;

import java.util.List;

public record BarbeiroResponseDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        List<String> especialidades,
        Boolean ativo
) {
}