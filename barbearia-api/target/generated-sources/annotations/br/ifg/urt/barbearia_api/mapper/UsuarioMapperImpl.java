package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.UsuarioRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.UsuarioResponseDTO;
import br.ifg.urt.barbearia_api.model.Usuario;
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
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        Long id = null;
        String nome = null;
        String email = null;
        String telefone = null;

        id = usuario.getId();
        nome = usuario.getNome();
        email = usuario.getEmail();
        telefone = usuario.getTelefone();

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO( id, nome, email, telefone );

        return usuarioResponseDTO;
    }

    @Override
    public List<UsuarioResponseDTO> toResponseDTOList(List<Usuario> usuarios) {
        if ( usuarios == null ) {
            return null;
        }

        List<UsuarioResponseDTO> list = new ArrayList<UsuarioResponseDTO>( usuarios.size() );
        for ( Usuario usuario : usuarios ) {
            list.add( toResponseDTO( usuario ) );
        }

        return list;
    }

    @Override
    public Usuario toEntity(UsuarioRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setNome( dto.nome() );
        usuario.setEmail( dto.email() );
        usuario.setTelefone( dto.telefone() );
        usuario.setSenha( dto.senha() );

        return usuario;
    }
}
