package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.AgendamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.AgendamentoResponseDTO;
import br.ifg.urt.barbearia_api.model.Agendamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AgendamentoMapper {

    // Avisa o MapStruct para ignorar o ID ao converter a requisição, pois o banco gera o ID sozinho
    @Mapping(target = "idAgendamento", ignore = true)
    Agendamento requestToEntity(AgendamentoRequestDTO dto);

    // Mapeia os campos que estão dentro de outros objetos (Ex: cliente.nome vai para nomeCliente)
    @Mapping(source = "cliente.nome", target = "nomeCliente")
    @Mapping(source = "barbeiro.nome", target = "nomeBarbeiro")
    @Mapping(source = "servico.nome", target = "nomeServico")
    AgendamentoResponseDTO entityToResponse(Agendamento entity);
}