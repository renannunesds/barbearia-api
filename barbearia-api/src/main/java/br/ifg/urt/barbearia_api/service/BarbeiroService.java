package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.BarbeiroRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.BarbeiroResponseDTO;
import br.ifg.urt.barbearia_api.mapper.BarbeiroMapper;
import br.ifg.urt.barbearia_api.model.Barbeiro;
import br.ifg.urt.barbearia_api.repository.BarbeiroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarbeiroService {

    private final BarbeiroRepository repository;
    private final BarbeiroMapper mapper;

    public BarbeiroService(
            BarbeiroRepository repository,
            BarbeiroMapper mapper) {

        this.repository = repository;
        this.mapper = mapper;
    }

    // Buscar todos
    public List<BarbeiroResponseDTO> findAll() {

        List<Barbeiro> barbeiros = repository.findAll();

        return mapper.toResponseDTOList(barbeiros);
    }

    // Buscar por ID
    public BarbeiroResponseDTO findById(Long id) {

        Barbeiro barbeiro = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Barbeiro não encontrado"));

        return mapper.toResponseDTO(barbeiro);
    }

    // Criar barbeiro
    public BarbeiroResponseDTO create(BarbeiroRequestDTO dto) {

        Barbeiro barbeiro = mapper.toEntity(dto);

        Barbeiro barbeiroSalvo = repository.save(barbeiro);

        return mapper.toResponseDTO(barbeiroSalvo);
    }

    // Atualizar barbeiro
    public BarbeiroResponseDTO update(Long id, BarbeiroRequestDTO dto) {

        Barbeiro barbeiroExistente = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Barbeiro não encontrado"));

        barbeiroExistente.setNome(dto.nome());
        barbeiroExistente.setEmail(dto.email());
        barbeiroExistente.setTelefone(dto.telefone());
        barbeiroExistente.setSenha(dto.senha());
        barbeiroExistente.setEspecialidade(dto.especialidade());
        barbeiroExistente.setAtivo(dto.ativo());

        Barbeiro atualizado = repository.save(barbeiroExistente);

        return mapper.toResponseDTO(atualizado);
    }

    // Ativar barbeiro
    public void ativarBarbeiro(Long id) {

        Barbeiro barbeiro = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Barbeiro não encontrado"));

        barbeiro.ativarBarbeiro();

        repository.save(barbeiro);
    }

    // Desativar barbeiro
    public void desativarBarbeiro(Long id) {

        Barbeiro barbeiro = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Barbeiro não encontrado"));

        barbeiro.desativarBarbeiro();

        repository.save(barbeiro);
    }

    // Deletar
    public void delete(Long id) {

        Barbeiro barbeiro = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Barbeiro não encontrado"));

        repository.delete(barbeiro);
    }
}