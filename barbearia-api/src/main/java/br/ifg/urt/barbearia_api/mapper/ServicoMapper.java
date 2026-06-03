package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.ServicoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ServicoResponseDTO;
import br.ifg.urt.barbearia_api.model.Servico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ServicoMapper {

    @Mapping(target = "valor", source = "valor.valor")
    @Mapping(target = "valorFormatado", expression = "java(servico.getValor() != null ? servico.getValor().getFormatado() : null)")
    ServicoResponseDTO toResponseDTO(Servico servico);

    List<ServicoResponseDTO> toResponseDTOList(List<Servico> servicos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "valor", expression = "java(new br.ifg.urt.barbearia_api.model.vo.PrecoVO(dto.valor()))")
    Servico toEntity(ServicoRequestDTO dto);
}