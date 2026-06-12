package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.UsuarioRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.UsuarioResponseDTO;
import br.ifg.urt.barbearia_api.model.Usuario;
import br.ifg.urt.barbearia_api.model.vo.EmailVO;
import br.ifg.urt.barbearia_api.model.vo.TelefoneVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-12T19:59:34-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Microsoft)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        String email = null;
        String telefone = null;
        Long id = null;
        String nome = null;

        email = usuarioEmailEndereco( usuario );
        telefone = usuarioTelefoneNumero( usuario );
        id = usuario.getId();
        nome = usuario.getNome();

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

        usuario.setEmail( new br.ifg.urt.barbearia_api.model.vo.EmailVO(dto.email()) );
        usuario.setTelefone( new br.ifg.urt.barbearia_api.model.vo.TelefoneVO(dto.telefone()) );
        usuario.setSenha( new br.ifg.urt.barbearia_api.model.vo.SenhaVO(dto.senha()) );

        return usuario;
    }

    private String usuarioEmailEndereco(Usuario usuario) {
        EmailVO email = usuario.getEmail();
        if ( email == null ) {
            return null;
        }
        return email.endereco();
    }

    private String usuarioTelefoneNumero(Usuario usuario) {
        TelefoneVO telefone = usuario.getTelefone();
        if ( telefone == null ) {
            return null;
        }
        return telefone.numero();
    }
}
