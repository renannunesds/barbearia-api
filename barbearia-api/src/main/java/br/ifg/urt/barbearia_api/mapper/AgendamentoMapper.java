package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.AgendamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.AgendamentoResponseDTO;
import br.ifg.urt.barbearia_api.model.Agendamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AgendamentoMapper {

    @Mapping(target = "idAgendamento", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "barbeiro", ignore = true)
    @Mapping(target = "servico", ignore = true)
    @Mapping(target = "pagamento", ignore = true)
    Agendamento requestToEntity(AgendamentoRequestDTO dto);

    @Mapping(source = "cliente.nome", target = "nomeCliente")
    @Mapping(source = "barbeiro.nome", target = "nomeBarbeiro")
    @Mapping(source = "servico.nome", target = "nomeServico")
    AgendamentoResponseDTO entityToResponse(Agendamento entity);
}