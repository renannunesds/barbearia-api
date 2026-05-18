package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.model.Usuario;
import br.ifg.urt.barbearia_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()) {
            return usuario.get();
        }

        return null;
    }

    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {

        Usuario usuarioExistente = buscarPorId(id);

        if (usuarioExistente != null) {

            usuarioExistente.setNome(usuarioAtualizado.getNome());
            usuarioExistente.setEmail(usuarioAtualizado.getEmail());
            usuarioExistente.setTelefone(usuarioAtualizado.getTelefone());
            usuarioExistente.setSenha(usuarioAtualizado.getSenha());

            return usuarioRepository.save(usuarioExistente);
        }

        return null;
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}