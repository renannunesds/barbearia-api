package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.PagamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.PagamentoResponseDTO;
import br.ifg.urt.barbearia_api.model.Pagamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PagamentoMapper {

    // 1. Ignora o ID e o objeto Agendamento ao converter a requisição (o service cuidará disso)
    @Mapping(target = "idPagamento", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    Pagamento requestToEntity(PagamentoRequestDTO dto);

    // 2. Busca o idAgendamento de dentro do objeto "agendamento" e joga direto no DTO de resposta
    @Mapping(source = "agendamento.idAgendamento", target = "idAgendamento")
    PagamentoResponseDTO entityToResponse(Pagamento entity);
}