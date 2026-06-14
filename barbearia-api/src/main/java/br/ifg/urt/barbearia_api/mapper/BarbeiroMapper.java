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
    // Como agora o Barbeiro se relaciona com Servicos, você pode mapear servicos aqui
    // Exemplo assumindo que Barbeiro tem uma lista de servicos:
    @Mapping(target = "servicos", expression = "java(barbeiro.getServicos() != null ? barbeiro.getServicos().stream().map(br.ifg.urt.barbearia_api.model.Servico::getNome).collect(java.util.stream.Collectors.toList()) : null)")
    BarbeiroResponseDTO toResponseDTO(Barbeiro barbeiro);

    List<BarbeiroResponseDTO> toResponseDTOList(List<Barbeiro> barbeiros);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", expression = "java(new br.ifg.urt.barbearia_api.model.vo.EmailVO(dto.email()))")
    @Mapping(target = "telefone", expression = "java(new br.ifg.urt.barbearia_api.model.vo.TelefoneVO(dto.telefone()))")
    @Mapping(target = "senha", expression = "java(new br.ifg.urt.barbearia_api.model.vo.SenhaVO(dto.senha()))")
    // Se no seu Barbeiro você mudou a lista de especialidades para servicos, o MapStruct
    // vai precisar que você ajuste o nome aqui. Se for mapeamento manual, pode precisar de @Mapping(target = "servicos", ignore = true)
    @Mapping(target = "servicos", ignore = true)
    Barbeiro toEntity(BarbeiroRequestDTO dto);
}