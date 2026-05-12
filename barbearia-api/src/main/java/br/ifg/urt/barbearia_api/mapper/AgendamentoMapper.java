package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.AgendamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.AgendamentoResponseDTO;
import br.ifg.urt.barbearia_api.model.Agendamento;
import org.springframework.stereotype.Component;

@Component
public class AgendamentoMapper {

    // Converte o que vem do Front-end para a Entidade do Banco
    public Agendamento toEntity(AgendamentoRequestDTO dto) {
        if (dto == null) return null;

        Agendamento agendamento = new Agendamento();
        agendamento.setData(dto.data());
        agendamento.setHorario(dto.horario());
        agendamento.setStatus("PENDENTE"); // Status inicial padrão
        return agendamento;
    }

    // Converte a Entidade do Banco para a resposta que o Front-end vai ler
    public AgendamentoResponseDTO toResponseDTO(Agendamento entity) {
        if (entity == null) return null;

        return new AgendamentoResponseDTO(
                entity.getIdAgendamento(),
                entity.getData(),
                entity.getHorario(),
                entity.getStatus(),
                entity.getCliente() != null ? entity.getCliente().getNome() : null,
                entity.getBarbeiro() != null ? entity.getBarbeiro().getNome() : null,
                entity.getServico() != null ? entity.getServico().getNome() : null
        );
    }
}