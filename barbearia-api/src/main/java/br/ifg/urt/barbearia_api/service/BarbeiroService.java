package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.BarbeiroRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.BarbeiroResponseDTO;
import br.ifg.urt.barbearia_api.mapper.BarbeiroMapper;
import br.ifg.urt.barbearia_api.model.Barbeiro;
import br.ifg.urt.barbearia_api.model.vo.EmailVO;
import br.ifg.urt.barbearia_api.model.vo.TelefoneVO;
import br.ifg.urt.barbearia_api.model.vo.SenhaVO;
import br.ifg.urt.barbearia_api.repository.BarbeiroRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BarbeiroService {

    private final BarbeiroRepository repository;
    private final BarbeiroMapper mapper;

    public BarbeiroService(BarbeiroRepository repository, BarbeiroMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<BarbeiroResponseDTO> findAll() {
        List<Barbeiro> barbeiros = repository.findAll();
        return mapper.toResponseDTOList(barbeiros);
    }

    public BarbeiroResponseDTO findById(Long id) {
        Barbeiro barbeiro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));
        return mapper.toResponseDTO(barbeiro);
    }

    public BarbeiroResponseDTO create(BarbeiroRequestDTO dto) {
        Barbeiro barbeiro = mapper.toEntity(dto);
        Barbeiro barbeiroSalvo = repository.save(barbeiro);
        return mapper.toResponseDTO(barbeiroSalvo);
    }

    public BarbeiroResponseDTO update(Long id, BarbeiroRequestDTO dto) {
        Barbeiro barbeiroExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));

        barbeiroExistente.setNome(dto.nome());
        barbeiroExistente.setEmail(new EmailVO(dto.email()));
        barbeiroExistente.setTelefone(new TelefoneVO(dto.telefone()));
        barbeiroExistente.setSenha(new SenhaVO(dto.senha()));
        barbeiroExistente.setEspecialidade(dto.especialidade());
        barbeiroExistente.setAtivo(dto.ativo());

        Barbeiro updated = repository.save(barbeiroExistente);
        return mapper.toResponseDTO(updated);
    }

    public void ativarBarbeiro(Long id) {
        Barbeiro barbeiro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));
        barbeiro.activarBarbeiro();
        repository.save(barbeiro);
    }

    public void desativarBarbeiro(Long id) {
        Barbeiro barbeiro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));
        barbeiro.desativarBarbeiro();
        repository.save(barbeiro);
    }

    public void delete(Long id) {
        Barbeiro barbeiro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));
        repository.delete(barbeiro);
    }
}