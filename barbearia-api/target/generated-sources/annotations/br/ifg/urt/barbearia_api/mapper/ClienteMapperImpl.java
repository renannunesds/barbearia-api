package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.ClienteRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ClienteResponseDTO;
import br.ifg.urt.barbearia_api.model.Cliente;
import br.ifg.urt.barbearia_api.model.vo.EmailVO;
import br.ifg.urt.barbearia_api.model.vo.TelefoneVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-09T10:37:34-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Microsoft)"
)
@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public ClienteResponseDTO toResponseDTO(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }

        String email = null;
        String telefone = null;
        Long id = null;
        String nome = null;
        String observacoes = null;

        email = clienteEmailEndereco( cliente );
        telefone = clienteTelefoneNumero( cliente );
        id = cliente.getId();
        nome = cliente.getNome();
        observacoes = cliente.getObservacoes();

        String telefoneFormatado = cliente.getTelefone() != null ? cliente.getTelefone().getFormatado() : null;

        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO( id, nome, email, telefone, telefoneFormatado, observacoes );

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
        cliente.setObservacoes( dto.observacoes() );

        cliente.setEmail( new br.ifg.urt.barbearia_api.model.vo.EmailVO(dto.email()) );
        cliente.setTelefone( new br.ifg.urt.barbearia_api.model.vo.TelefoneVO(dto.telefone()) );
        cliente.setSenha( new br.ifg.urt.barbearia_api.model.vo.SenhaVO(dto.senha()) );

        return cliente;
    }

    private String clienteEmailEndereco(Cliente cliente) {
        EmailVO email = cliente.getEmail();
        if ( email == null ) {
            return null;
        }
        return email.endereco();
    }

    private String clienteTelefoneNumero(Cliente cliente) {
        TelefoneVO telefone = cliente.getTelefone();
        if ( telefone == null ) {
            return null;
        }
        return telefone.numero();
    }
}
