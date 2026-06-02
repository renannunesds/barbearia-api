package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.Barbeiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BarbeiroRepository extends JpaRepository<Barbeiro, Long> {

    default Barbeiro findByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado com ID: " + id));
    }

    Optional<Barbeiro> findByEmailEndereco(String email);

    List<Barbeiro> findByNomeContainingIgnoreCase(String nome);

    List<Barbeiro> findByAtivoTrue();

    List<Barbeiro> findByEspecialidadeContainingIgnoreCase(String especialidade);

    List<Barbeiro> findAllByOrderByNomeAsc();
}