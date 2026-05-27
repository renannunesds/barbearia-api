package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.AgendamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.AgendamentoResponseDTO;
import br.ifg.urt.barbearia_api.model.Agendamento;
import org.springframework.stereotype.Component;

@Component // Esta anotação avisa o Spring que a sua classe de mapeamento existe
public class AgendamentoMapper {

    public Agendamento requestToEntity(AgendamentoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setData(dto.data());
        agendamento.setHorario(dto.horario());

        return agendamento;
    }

    public AgendamentoResponseDTO entityToResponse(Agendamento entity) {
        if (entity == null) {
            return null;
        }

        String nomeCliente = (entity.getCliente() != null) ? entity.getCliente().getNome() : null;
        String nomeBarbeiro = (entity.getBarbeiro() != null) ? entity.getBarbeiro().getNome() : null;
        String nomeServico = (entity.getServico() != null) ? entity.getServico().getNome() : null;

        return new AgendamentoResponseDTO(
                entity.getIdAgendamento(), // Seu getter correto do Model!
                entity.getData(),
                entity.getHorario(),
                entity.getStatus(),
                nomeCliente,
                nomeBarbeiro,
                nomeServico
        );
    }
}