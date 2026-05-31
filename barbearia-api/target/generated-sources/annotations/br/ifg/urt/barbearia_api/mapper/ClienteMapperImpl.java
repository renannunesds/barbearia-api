package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.ClienteRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ClienteResponseDTO;
import br.ifg.urt.barbearia_api.model.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-31T17:48:24-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Microsoft)"
)
@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public ClienteResponseDTO toResponseDTO(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }

        Long id = null;
        String nome = null;
        String email = null;
        String telefone = null;
        String observacoes = null;

        id = cliente.getId();
        nome = cliente.getNome();
        email = cliente.getEmail();
        telefone = cliente.getTelefone();
        observacoes = cliente.getObservacoes();

        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO( id, nome, email, telefone, observacoes );

        return clienteResponseDTO;
    }

    @Override
    public List<ClienteResponseDTO> toResponseDTOList(List<Cliente> clientes) {
        if ( clientes == null ) {
            return null;
        }

        List<ClienteResponseDTO> list = new ArrayList<ClienteResponseDTO>( clientes.size() );
        for ( Cliente cliente : clientes ) {
            list.add( toResponseDTO( cliente ) );
        }

        return list;
    }

    @Override
    public Cliente toEntity(ClienteRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setNome( dto.nome() );
        cliente.setEmail( dto.email() );
        cliente.setTelefone( dto.telefone() );
        cliente.setSenha( dto.senha() );
        cliente.setObservacoes( dto.observacoes() );

        return cliente;
    }
}
