package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.ServicoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ServicoResponseDTO;
import br.ifg.urt.barbearia_api.model.Servico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ServicoMapper {

    ServicoResponseDTO toResponseDTO(Servico servico);

    List<ServicoResponseDTO> toResponseDTOList(List<Servico> servicos);

    @Mapping(target = "idItem", ignore = true)
    Servico toEntity(ServicoRequestDTO dto);
}