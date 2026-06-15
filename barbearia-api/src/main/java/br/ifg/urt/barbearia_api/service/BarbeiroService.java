package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.BarbeiroRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.BarbeiroResponseDTO;
import br.ifg.urt.barbearia_api.mapper.BarbeiroMapper;
import br.ifg.urt.barbearia_api.model.Barbeiro;
import br.ifg.urt.barbearia_api.model.Servico; // Importe a classe Servico
import br.ifg.urt.barbearia_api.model.vo.EmailVO;
import br.ifg.urt.barbearia_api.model.vo.TelefoneVO;
import br.ifg.urt.barbearia_api.model.vo.SenhaVO;
import br.ifg.urt.barbearia_api.repository.BarbeiroRepository;
import br.ifg.urt.barbearia_api.repository.ServicoRepository; // Use o repositório de Servico
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BarbeiroService {

    private final BarbeiroRepository repository;
    private final ServicoRepository servicoRepository;
    private final BarbeiroMapper mapper;

    public BarbeiroService(BarbeiroRepository repository, ServicoRepository servicoRepository, BarbeiroMapper mapper) {
        this.repository = repository;
        this.servicoRepository = servicoRepository;
        this.mapper = mapper;
    }

    public Page<BarbeiroResponseDTO> findAll(String nome, Pageable pageable) {
        if (nome != null && !nome.isBlank()) {
            return repository.findByNomeContainingIgnoreCase(nome, pageable).map(mapper::toResponseDTO);
        }
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    public BarbeiroResponseDTO findById(Long id) {
        Barbeiro barbeiro = repository.findByIdOrThrow(id);
        return mapper.toResponseDTO(barbeiro);
    }

    @Transactional
    public BarbeiroResponseDTO create(BarbeiroRequestDTO dto) {
        Barbeiro barbeiro = mapper.toEntity(dto);

        if (dto.servicos() != null && !dto.servicos().isEmpty()) {
            List<Servico> servicosCarregados = servicoRepository.findAllById(dto.servicos());
            barbeiro.setServicos(servicosCarregados);
        } else {
            barbeiro.setServicos(new ArrayList<>());
        }

        return mapper.toResponseDTO(repository.save(barbeiro));
    }

    @Transactional
    public BarbeiroResponseDTO update(Long id, BarbeiroRequestDTO dto) {
        Barbeiro barbeiroExistente = repository.findByIdOrThrow(id);

        barbeiroExistente.setNome(dto.nome());
        barbeiroExistente.setEmail(new EmailVO(dto.email()));
        barbeiroExistente.setTelefone(new TelefoneVO(dto.telefone()));
        barbeiroExistente.setSenha(new SenhaVO(dto.senha()));
        barbeiroExistente.setAtivo(dto.ativo());

        if (dto.servicos() != null) {
            barbeiroExistente.setServicos(servicoRepository.findAllById(dto.servicos()));
        }

        return mapper.toResponseDTO(repository.save(barbeiroExistente));
    }

    @Transactional
    public void ativarBarbeiro(Long id) {
        Barbeiro barbeiro = repository.findByIdOrThrow(id);
        barbeiro.activarBarbeiro();
        repository.save(barbeiro);
    }

    @Transactional
    public void desativarBarbeiro(Long id) {
        Barbeiro barbeiro = repository.findByIdOrThrow(id);
        barbeiro.desativarBarbeiro();
        repository.save(barbeiro);
    }

    @Transactional
    public void delete(Long id) {
        Barbeiro barbeiro = repository.findByIdOrThrow(id);
        repository.delete(barbeiro);
    }
}