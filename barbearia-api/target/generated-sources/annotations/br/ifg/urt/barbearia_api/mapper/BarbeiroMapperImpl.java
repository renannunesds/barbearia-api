package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.BarbeiroRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.BarbeiroResponseDTO;
import br.ifg.urt.barbearia_api.model.Barbeiro;
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
public class BarbeiroMapperImpl implements BarbeiroMapper {

    @Override
    public BarbeiroResponseDTO toResponseDTO(Barbeiro barbeiro) {
        if ( barbeiro == null ) {
            return null;
        }

        Long id = null;
        String nome = null;
        String email = null;
        String telefone = null;
        String especialidade = null;
        Boolean ativo = null;

        id = barbeiro.getId();
        nome = barbeiro.getNome();
        email = barbeiro.getEmail();
        telefone = barbeiro.getTelefone();
        especialidade = barbeiro.getEspecialidade();
        ativo = barbeiro.getAtivo();

        BarbeiroResponseDTO barbeiroResponseDTO = new BarbeiroResponseDTO( id, nome, email, telefone, especialidade, ativo );

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
        barbeiro.setEmail( dto.email() );
        barbeiro.setTelefone( dto.telefone() );
        barbeiro.setSenha( dto.senha() );
        barbeiro.setEspecialidade( dto.especialidade() );
        barbeiro.setAtivo( dto.ativo() );

        return barbeiro;
    }
}
