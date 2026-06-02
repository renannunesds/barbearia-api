package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.UsuarioRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.UsuarioResponseDTO;
import br.ifg.urt.barbearia_api.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "email", source = "email.endereco")
    @Mapping(target = "telefone", source = "telefone.numero")
    UsuarioResponseDTO toResponseDTO(Usuario usuario);

    List<UsuarioResponseDTO> toResponseDTOList(List<Usuario> usuarios);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", expression = "java(new br.ifg.urt.barbearia_api.model.vo.EmailVO(dto.email()))")
    @Mapping(target = "telefone", expression = "java(new br.ifg.urt.barbearia_api.model.vo.TelefoneVO(dto.telefone()))")
    @Mapping(target = "senha", expression = "java(new br.ifg.urt.barbearia_api.model.vo.SenhaVO(dto.senha()))")
    Usuario toEntity(UsuarioRequestDTO dto);
}