package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.BarbeiroRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.BarbeiroResponseDTO;
import br.ifg.urt.barbearia_api.model.Barbeiro;
import br.ifg.urt.barbearia_api.model.vo.EmailVO;
import br.ifg.urt.barbearia_api.model.vo.TelefoneVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-13T15:25:13-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Microsoft)"
)
@Component
public class BarbeiroMapperImpl implements BarbeiroMapper {

    @Override
    public BarbeiroResponseDTO toResponseDTO(Barbeiro barbeiro) {
        if ( barbeiro == null ) {
            return null;
        }

        String email = null;
        String telefone = null;
        Long id = null;
        String nome = null;
        Boolean ativo = null;

        email = barbeiroEmailEndereco( barbeiro );
        telefone = barbeiroTelefoneNumero( barbeiro );
        id = barbeiro.getId();
        nome = barbeiro.getNome();
        ativo = barbeiro.getAtivo();

        List<String> especialidades = barbeiro.getEspecialidades() != null ? barbeiro.getEspecialidades().stream().map(br.ifg.urt.barbearia_api.model.Especialidade::getNome).collect(java.util.stream.Collectors.toList()) : null;

        BarbeiroResponseDTO barbeiroResponseDTO = new BarbeiroResponseDTO( id, nome, email, telefone, especialidades, ativo );

        return barbeiroResponseDTO;
    }

    @Override
    public List<BarbeiroResponseDTO> toResponseDTOList(List<Barbeiro> barbeiros) {
        if ( barbeiros == null ) {
            return null;
        }

        List<BarbeiroResponseDTO> list = new ArrayList<BarbeiroResponseDTO>( barbeiros.size() );
        for ( Barbeiro barbeiro : barbeiros ) {
            list.add( toResponseDTO( barbeiro ) );
        }

        return list;
    }

    @Override
    public Barbeiro toEntity(BarbeiroRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Barbeiro barbeiro = new Barbeiro();

        barbeiro.setNome( dto.nome() );
        barbeiro.setAtivo( dto.ativo() );

        barbeiro.setEmail( new br.ifg.urt.barbearia_api.model.vo.EmailVO(dto.email()) );
        barbeiro.setTelefone( new br.ifg.urt.barbearia_api.model.vo.TelefoneVO(dto.telefone()) );
        barbeiro.setSenha( new br.ifg.urt.barbearia_api.model.vo.SenhaVO(dto.senha()) );

        return barbeiro;
    }

    private String barbeiroEmailEndereco(Barbeiro barbeiro) {
        EmailVO email = barbeiro.getEmail();
        if ( email == null ) {
            return null;
        }
        return email.endereco();
    }

    private String barbeiroTelefoneNumero(Barbeiro barbeiro) {
        TelefoneVO telefone = barbeiro.getTelefone();
        if ( telefone == null ) {
            return null;
        }
        return telefone.numero();
    }
}
