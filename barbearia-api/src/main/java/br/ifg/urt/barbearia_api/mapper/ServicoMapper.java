package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.ServicoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ServicoResponseDTO;
import br.ifg.urt.barbearia_api.model.Servico;

public class ServicoMapper {

    public static Servico toEntity(ServicoRequestDTO dto) {
        Servico servico = new Servico();
        servico.setNome(dto.nome());
        servico.setDescricao(dto.descricao());
        servico.setValor(dto.valor());
        servico.setDuracaoMinutos(dto.duracaoMinutos());
        return servico;
    }

    public static ServicoResponseDTO toResponse(Servico servico) {
        return new ServicoResponseDTO(
                servico.getIdItem(),
                servico.getNome(),
                servico.getDescricao(),
                servico.getValor(),
                servico.getDuracaoMinutos()
        );
    }
}
