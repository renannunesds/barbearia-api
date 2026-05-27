package br.ifg.urt.barbearia_api.mapper;

import br.ifg.urt.barbearia_api.dto.request.PagamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.PagamentoResponseDTO;
import br.ifg.urt.barbearia_api.model.Pagamento;
import org.springframework.stereotype.Component;

@Component // Avisa o Spring que esta classe de mapeamento de pagamento existe
public class PagamentoMapper {

    // Transforma o DTO que vem da requisição na Entidade Pagamento
    public Pagamento requestToEntity(PagamentoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setValorTotal(dto.valorTotal());
        pagamento.setDataPagamento(dto.dataPagamento());
        pagamento.setFormaPagamento(dto.formaPagamento());
        pagamento.setStatus(dto.status());

        // Nota: O relacionamento com o Agendamento será feito no Service,
        // buscando o agendamento no banco pelo ID enviado no DTO.

        return pagamento;
    }

    // Transforma a Entidade Pagamento salva no banco no DTO de resposta
    public PagamentoResponseDTO entityToResponse(Pagamento entity) {
        if (entity == null) {
            return null;
        }

        // Pega o ID do agendamento com segurança (evita NullPointerException se não houver agendamento)
        Long idAgendamento = (entity.getAgendamento() != null) ? entity.getAgendamento().getIdAgendamento() : null;

        return new PagamentoResponseDTO(
                entity.getIdPagamento(), // Seu getter correto do Model Pagamento
                entity.getValorTotal(),
                entity.getDataPagamento(),
                entity.getFormaPagamento(),
                entity.getStatus(),
                idAgendamento
        );
    }
}