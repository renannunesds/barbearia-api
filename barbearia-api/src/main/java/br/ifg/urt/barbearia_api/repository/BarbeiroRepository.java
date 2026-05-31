package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.Barbeiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BarbeiroRepository extends JpaRepository<Barbeiro, Long> {

    // Buscar barbeiro por ID ou lançar exceção
    default Barbeiro findByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Barbeiro não encontrado com ID: " + id));
    }

    // Buscar por email
    Optional<Barbeiro> findByEmail(String email);

    // Buscar por nome
    List<Barbeiro> findByNomeContainingIgnoreCase(String nome);

    // Buscar barbeiros ativos
    List<Barbeiro> findByAtivoTrue();

    // Buscar por especialidade
    List<Barbeiro> findByEspecialidadeContainingIgnoreCase(String especialidade);

    // Ordenar por nome
    List<Barbeiro> findAllByOrderByNomeAsc();
}