package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.ClienteRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ClienteResponseDTO;
import br.ifg.urt.barbearia_api.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteResponseDTO toResponseDTO(Cliente cliente);

    List<ClienteResponseDTO> toResponseDTOList(List<Cliente> clientes);

    @Mapping(target = "id", ignore = true)
    Cliente toEntity(ClienteRequestDTO dto);
}