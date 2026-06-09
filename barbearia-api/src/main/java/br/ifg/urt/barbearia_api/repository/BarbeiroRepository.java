package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.Barbeiro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // ATUALIZADO: Agora retorna Page e aceita Pageable
    Page<Barbeiro> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    List<Barbeiro> findByAtivoTrue();

    @Query("SELECT b FROM Barbeiro b JOIN b.especialidades e WHERE LOWER(e.nome) LIKE LOWER(CONCAT('%', :especialidade, '%'))")
    List<Barbeiro> findByEspecialidadeContainingIgnoreCase(@Param("especialidade") String especialidade);

    List<Barbeiro> findAllByOrderByNomeAsc();
}