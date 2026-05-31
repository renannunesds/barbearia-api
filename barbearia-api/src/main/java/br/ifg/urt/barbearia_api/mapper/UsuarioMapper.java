package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.UsuarioRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.UsuarioResponseDTO;
import br.ifg.urt.barbearia_api.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioResponseDTO toResponseDTO(Usuario usuario);

    List<UsuarioResponseDTO> toResponseDTOList(List<Usuario> usuarios);

    @Mapping(target = "id", ignore = true)
    Usuario toEntity(UsuarioRequestDTO dto);
}