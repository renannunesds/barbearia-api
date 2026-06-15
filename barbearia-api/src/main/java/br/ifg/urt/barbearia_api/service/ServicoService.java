package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.ServicoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ServicoResponseDTO;
import br.ifg.urt.barbearia_api.mapper.ServicoMapper;
import br.ifg.urt.barbearia_api.model.Servico;
import br.ifg.urt.barbearia_api.repository.ServicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final ServicoMapper servicoMapper;

    public ServicoService(ServicoRepository servicoRepository, ServicoMapper servicoMapper) {
        this.servicoRepository = servicoRepository;
        this.servicoMapper = servicoMapper;
    }

    @Transactional
    public ServicoResponseDTO criar(ServicoRequestDTO dto) {
        Servico servico = servicoMapper.toEntity(dto);
        return servicoMapper.toResponseDTO(servicoRepository.save(servico));
    }

    public Page<ServicoResponseDTO> listar(String nome, Pageable pageable) {
        Page<Servico> servicosPage;
        if (nome != null && !nome.isBlank()) {
            servicosPage = servicoRepository.findByNomeContainingIgnoreCase(nome, pageable);
        } else {
            servicosPage = servicoRepository.findAll(pageable);
        }
        return servicosPage.map(servicoMapper::toResponseDTO);
    }

    public ServicoResponseDTO buscarPorId(Long id) {
        Servico servico = servicoRepository.findByIdOrThrow(id);
        return servicoMapper.toResponseDTO(servico);
    }

    @Transactional
    public ServicoResponseDTO atualizar(Long id, ServicoRequestDTO dto) {
        Servico servico = servicoRepository.findByIdOrThrow(id);

        servico.setNome(dto.nome());
        servico.setDescricao(dto.descricao());
        servico.setDuracaoMinutos(dto.duracaoMinutos());

        return servicoMapper.toResponseDTO(servicoRepository.save(servico));
    }

    @Transactional
    public void deletar(Long id) {
        Servico servico = servicoRepository.findByIdOrThrow(id);
        servicoRepository.delete(servico);
    }
}