package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.Barbeiro;
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

    List<Barbeiro> findByNomeContainingIgnoreCase(String nome);

    List<Barbeiro> findByAtivoTrue();

    // CORRIGIDO: Como 'especialidades' agora é uma lista (N:N), usamos @Query com JOIN
    // para buscar pelo nome da especialidade dentro da lista de forma correta
    @Query("SELECT b FROM Barbeiro b JOIN b.especialidades e WHERE LOWER(e.nome) LIKE LOWER(CONCAT('%', :especialidade, '%'))")
    List<Barbeiro> findByEspecialidadeContainingIgnoreCase(@Param("especialidade") String especialidade);

    List<Barbeiro> findAllByOrderByNomeAsc();
}