package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.BarbeiroRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.BarbeiroResponseDTO;
import br.ifg.urt.barbearia_api.model.Barbeiro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BarbeiroMapper {

    BarbeiroResponseDTO toResponseDTO(Barbeiro barbeiro);

    List<BarbeiroResponseDTO> toResponseDTOList(List<Barbeiro> barbeiros);

    @Mapping(target = "id", ignore = true)
    Barbeiro toEntity(BarbeiroRequestDTO dto);
}