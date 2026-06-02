package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.ClienteRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ClienteResponseDTO;
import br.ifg.urt.barbearia_api.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(target = "email", source = "email.endereco")
    @Mapping(target = "telefone", source = "telefone.numero")
    @Mapping(target = "telefoneFormatado", expression = "java(cliente.getTelefone() != null ? cliente.getTelefone().getFormatado() : null)")
    ClienteResponseDTO toResponseDTO(Cliente cliente);

    List<ClienteResponseDTO> toResponseDTOList(List<Cliente> clientes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", expression = "java(new br.ifg.urt.barbearia_api.model.vo.EmailVO(dto.email()))")
    @Mapping(target = "telefone", expression = "java(new br.ifg.urt.barbearia_api.model.vo.TelefoneVO(dto.telefone()))")
    @Mapping(target = "senha", expression = "java(new br.ifg.urt.barbearia_api.model.vo.SenhaVO(dto.senha()))")
    Cliente toEntity(ClienteRequestDTO dto);
}