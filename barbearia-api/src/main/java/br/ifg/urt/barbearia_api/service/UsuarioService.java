package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.UsuarioRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.UsuarioResponseDTO;
import br.ifg.urt.barbearia_api.mapper.UsuarioMapper;
import br.ifg.urt.barbearia_api.model.Usuario;
import br.ifg.urt.barbearia_api.model.vo.EmailVO;
import br.ifg.urt.barbearia_api.model.vo.TelefoneVO;
import br.ifg.urt.barbearia_api.model.vo.SenhaVO;
import br.ifg.urt.barbearia_api.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    public UsuarioService(UsuarioRepository repository, UsuarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // ATUALIZADO: Listagem global paginada
    public Page<UsuarioResponseDTO> findAll(Pageable pageable) {
        Page<Usuario> usuariosPage = repository.findAll(pageable);
        return usuariosPage.map(mapper::toResponseDTO);
    }

    public UsuarioResponseDTO findById(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return mapper.toResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO create(UsuarioRequestDTO dto) {
        Usuario usuario = mapper.toEntity(dto);
        Usuario usuarioSalvo = repository.save(usuario);
        return mapper.toResponseDTO(usuarioSalvo);
    }

    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO dto) {
        Usuario usuarioExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioExistente.setNome(dto.nome());
        usuarioExistente.setEmail(new EmailVO(dto.email()));
        usuarioExistente.setTelefone(new TelefoneVO(dto.telefone()));
        usuarioExistente.setSenha(new SenhaVO(dto.senha()));

        Usuario updated = repository.save(usuarioExistente);
        return mapper.toResponseDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        repository.delete(usuario);
    }
}