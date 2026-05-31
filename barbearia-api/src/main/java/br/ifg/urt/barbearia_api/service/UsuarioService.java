package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.UsuarioRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.UsuarioResponseDTO;
import br.ifg.urt.barbearia_api.mapper.UsuarioMapper;
import br.ifg.urt.barbearia_api.model.Usuario;
import br.ifg.urt.barbearia_api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    public UsuarioService(
            UsuarioRepository repository,
            UsuarioMapper mapper) {

        this.repository = repository;
        this.mapper = mapper;
    }

    // Buscar todos
    public List<UsuarioResponseDTO> findAll() {

        List<Usuario> usuarios = repository.findAll();

        return mapper.toResponseDTOList(usuarios);
    }

    // Buscar por ID
    public UsuarioResponseDTO findById(Long id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        return mapper.toResponseDTO(usuario);
    }

    // Criar usuário
    public UsuarioResponseDTO create(UsuarioRequestDTO dto) {

        Usuario usuario = mapper.toEntity(dto);

        Usuario usuarioSalvo = repository.save(usuario);

        return mapper.toResponseDTO(usuarioSalvo);
    }

    // Atualizar usuário
    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO dto) {

        Usuario usuarioExistente = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        usuarioExistente.setNome(dto.nome());
        usuarioExistente.setEmail(dto.email());
        usuarioExistente.setTelefone(dto.telefone());
        usuarioExistente.setSenha(dto.senha());

        Usuario atualizado = repository.save(usuarioExistente);

        return mapper.toResponseDTO(atualizado);
    }

    // Deletar usuário
    public void delete(Long id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        repository.delete(usuario);
    }
}