package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.ServicoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ServicoResponseDTO;
import br.ifg.urt.barbearia_api.mapper.ServicoMapper;
import br.ifg.urt.barbearia_api.model.Servico;
import br.ifg.urt.barbearia_api.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final ServicoMapper servicoMapper; // Injetando o novo Mapper do MapStruct

    public ServicoService(ServicoRepository servicoRepository, ServicoMapper servicoMapper) {
        this.servicoRepository = servicoRepository;
        this.servicoMapper = servicoMapper;
    }

    public ServicoResponseDTO criar(ServicoRequestDTO dto) {
        Servico servico = servicoMapper.toEntity(dto);
        return servicoMapper.toResponseDTO(servicoRepository.save(servico));
    }

    public List<ServicoResponseDTO> listar() {
        return servicoMapper.toResponseDTOList(servicoRepository.findAll());
    }

    public ServicoResponseDTO buscarPorId(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        return servicoMapper.toResponseDTO(servico);
    }

    public ServicoResponseDTO atualizar(Long id, ServicoRequestDTO dto) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        servico.setNome(dto.nome());
        servico.setDescricao(dto.descricao());
        servico.setDuracaoMinutos(dto.duracaoMinutos());

        return servicoMapper.toResponseDTO(servicoRepository.save(servico));
    }

    public void deletar(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        servicoRepository.delete(servico);
    }
}