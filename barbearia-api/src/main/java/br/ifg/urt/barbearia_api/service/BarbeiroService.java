package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.BarbeiroRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.BarbeiroResponseDTO;
import br.ifg.urt.barbearia_api.mapper.BarbeiroMapper;
import br.ifg.urt.barbearia_api.model.Barbeiro;
import br.ifg.urt.barbearia_api.model.Especialidade;
import br.ifg.urt.barbearia_api.model.vo.EmailVO;
import br.ifg.urt.barbearia_api.model.vo.TelefoneVO;
import br.ifg.urt.barbearia_api.model.vo.SenhaVO;
import br.ifg.urt.barbearia_api.repository.BarbeiroRepository;
import br.ifg.urt.barbearia_api.repository.EspecialidadeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BarbeiroService {

    private final BarbeiroRepository repository;
    private final EspecialidadeRepository especialidadeRepository;
    private final BarbeiroMapper mapper;

    public BarbeiroService(BarbeiroRepository repository, EspecialidadeRepository especialidadeRepository, BarbeiroMapper mapper) {
        this.repository = repository;
        this.especialidadeRepository = especialidadeRepository;
        this.mapper = mapper;
    }

    // ATUALIZADO: Filtra por nome se for enviado, senão traz todos paginados
    public Page<BarbeiroResponseDTO> findAll(String nome, Pageable pageable) {
        Page<Barbeiro> barbeirosPage;

        if (nome != null && !nome.isBlank()) {
            barbeirosPage = repository.findByNomeContainingIgnoreCase(nome, pageable);
        } else {
            barbeirosPage = repository.findAll(pageable);
        }

        return barbeirosPage.map(mapper::toResponseDTO);
    }

    public BarbeiroResponseDTO findById(Long id) {
        Barbeiro barbeiro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));
        return mapper.toResponseDTO(barbeiro);
    }

    @Transactional
    public BarbeiroResponseDTO create(BarbeiroRequestDTO dto) {
        Barbeiro barbeiro = mapper.toEntity(dto);

        if (dto.especialidades() != null && !dto.especialidades().isEmpty()) {
            List<Especialidade> especialidadesCarregadas = especialidadeRepository.findAllById(dto.especialidades());
            barbeiro.setEspecialidades(especialidadesCarregadas);
        } else {
            barbeiro.setEspecialidades(new ArrayList<>());
        }

        Barbeiro barbeiroSalvo = repository.save(barbeiro);
        return mapper.toResponseDTO(barbeiroSalvo);
    }

    @Transactional
    public BarbeiroResponseDTO update(Long id, BarbeiroRequestDTO dto) {
        Barbeiro barbeiroExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));

        barbeiroExistente.setNome(dto.nome());
        barbeiroExistente.setEmail(new EmailVO(dto.email()));
        barbeiroExistente.setTelefone(new TelefoneVO(dto.telefone()));
        barbeiroExistente.setSenha(new SenhaVO(dto.senha()));
        barbeiroExistente.setAtivo(dto.ativo());

        if (dto.especialidades() != null) {
            List<Especialidade> especialidadesCarregadas = especialidadeRepository.findAllById(dto.especialidades());
            barbeiroExistente.setEspecialidades(especialidadesCarregadas);
        }

        Barbeiro updated = repository.save(barbeiroExistente);
        return mapper.toResponseDTO(updated);
    }

    @Transactional
    public void ativarBarbeiro(Long id) {
        Barbeiro barbeiro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));
        barbeiro.activarBarbeiro();
        repository.save(barbeiro);
    }

    @Transactional
    public void desativarBarbeiro(Long id) {
        Barbeiro barbeiro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));
        barbeiro.desativarBarbeiro();
        repository.save(barbeiro);
    }

    @Transactional
    public void delete(Long id) {
        Barbeiro barbeiro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));
        repository.delete(barbeiro);
    }
}