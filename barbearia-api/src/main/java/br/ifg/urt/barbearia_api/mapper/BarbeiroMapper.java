package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.BarbeiroRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.BarbeiroResponseDTO;
import br.ifg.urt.barbearia_api.model.Barbeiro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BarbeiroMapper {

    @Mapping(target = "email", source = "email.endereco")
    @Mapping(target = "telefone", source = "telefone.numero")
    @Mapping(target = "especialidades", expression = "java(barbeiro.getEspecialidades() != null ? barbeiro.getEspecialidades().stream().map(br.ifg.urt.barbearia_api.model.Especialidade::getNome).collect(java.util.stream.Collectors.toList()) : null)")
    BarbeiroResponseDTO toResponseDTO(Barbeiro barbeiro);

    List<BarbeiroResponseDTO> toResponseDTOList(List<Barbeiro> barbeiros);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", expression = "java(new br.ifg.urt.barbearia_api.model.vo.EmailVO(dto.email()))")
    @Mapping(target = "telefone", expression = "java(new br.ifg.urt.barbearia_api.model.vo.TelefoneVO(dto.telefone()))")
    @Mapping(target = "senha", expression = "java(new br.ifg.urt.barbearia_api.model.vo.SenhaVO(dto.senha()))")
    @Mapping(target = "especialidades", ignore = true)
    Barbeiro toEntity(BarbeiroRequestDTO dto);
}