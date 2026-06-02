package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    default Usuario findByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
    }

    Optional<Usuario> findByEmailEndereco(String email);

    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    List<Usuario> findAllByOrderByNomeAsc();
}