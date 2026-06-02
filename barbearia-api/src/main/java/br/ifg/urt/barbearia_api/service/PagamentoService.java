package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.PagamentoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.PagamentoResponseDTO;
import br.ifg.urt.barbearia_api.mapper.PagamentoMapper;
import br.ifg.urt.barbearia_api.model.Agendamento;
import br.ifg.urt.barbearia_api.model.Pagamento;
import br.ifg.urt.barbearia_api.repository.AgendamentoRepository;
import br.ifg.urt.barbearia_api.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentoService {

    // 1. Atributos como final (Injeção por construtor limpa e segura)
    private final PagamentoRepository pagamentoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final PagamentoMapper pagamentoMapper;

    // Construtor oficial eliminando os @Autowired soltos
    public PagamentoService(PagamentoRepository pagamentoRepository,
                            AgendamentoRepository agendamentoRepository,
                            PagamentoMapper pagamentoMapper) {
        this.pagamentoRepository = pagamentoRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.pagamentoMapper = pagamentoMapper;
    }

    // 2. PROCESSAR/CRIAR PAGAMENTO
    public PagamentoResponseDTO processarPagamento(PagamentoRequestDTO dto) {
        // Busca o agendamento correspondente
        Agendamento agendamento = agendamentoRepository.findById(dto.idAgendamento())
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado para este pagamento"));

        // Atualiza o status do agendamento para concluído
        agendamento.setStatus("CONCLUIDO");
        agendamentoRepository.save(agendamento);

        // Converte o DTO para a Entidade Pagamento e vincula o agendamento
        Pagamento pagamento = pagamentoMapper.requestToEntity(dto);
        pagamento.setAgendamento(agendamento);

        // Uso do método de negócio da Entidade (Define automaticamente o status como "PAGO")
        pagamento.confirmarPagamento();

        // Salva e retorna o DTO de resposta
        Pagamento salvo = pagamentoRepository.save(pagamento);
        return pagamentoMapper.entityToResponse(salvo);
    }

    // 3. LISTAR TODOS OS PAGAMENTOS (Necessário para a rota GET do Controller)
    public List<PagamentoResponseDTO> listarTodos() {
        return pagamentoRepository.findAll().stream()
                .map(pagamentoMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    // 4. BUSCAR PAGAMENTO POR ID (Necessário para a rota GET /{id} do Controller)
    public PagamentoResponseDTO buscarPorId(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento de ID " + id + " não encontrado!"));
        return pagamentoMapper.entityToResponse(pagamento);
    }
}