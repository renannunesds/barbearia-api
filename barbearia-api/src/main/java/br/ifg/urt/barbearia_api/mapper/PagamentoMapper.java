//package br.ifg.urt.barbearia_api.mapper;
//
//import br.ifg.urt.barbearia_api.dto.request.PagamentoRequestDTO;
//import br.ifg.urt.barbearia_api.dto.response.PagamentoResponseDTO;
//import br.ifg.urt.barbearia_api.model.Pagamento;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PagamentoMapper {
//
//    public Pagamento toEntity(PagamentoRequestDTO dto) {
//        if (dto == null) return null;
//
//        Pagamento pagamento = new Pagamento();
//        pagamento.setValorTotal(dto.valorTotal());
//        pagamento.setDataPagamento(dto.dataPagamento());
//        pagamento.setFormaPagamento(dto.formaPagamento());
//        pagamento.setStatus(dto.status());
//        return pagamento;
//    }
//
//    public PagamentoResponseDTO toResponseDTO(Pagamento entity) {
//        if (entity == null) return null;
//
//        return new PagamentoResponseDTO(
//                entity.getIdPagamento(),
//                entity.getValorTotal(),
//                entity.getDataPagamento(),
//                entity.getFormaPagamento(),
//                entity.getStatus(),
//                entity.getAgendamento() != null ? entity.getAgendamento().getIdAgendamento() : null
//        );
//    }
//}