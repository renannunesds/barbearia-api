package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.PagamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.PagamentoResponseDTO;
import br.ifg.urt.barbearia_api.model.Pagamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PagamentoMapper {

    @Mapping(target = "idPagamento", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    Pagamento requestToEntity(PagamentoRequestDTO dto);

    @Mapping(source = "agendamento.idAgendamento", target = "idAgendamento")
    PagamentoResponseDTO entityToResponse(Pagamento entity);
}