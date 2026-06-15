package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.Barbeiro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BarbeiroRepository extends JpaRepository<Barbeiro, Long> {

    @Override
    @EntityGraph(attributePaths = {"servicos"})
    Optional<Barbeiro> findById(Long id);

    default Barbeiro findByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado com ID: " + id));
    }

    Optional<Barbeiro> findByEmailEndereco(String email);

    @EntityGraph(attributePaths = {"servicos"})
    Page<Barbeiro> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"servicos"})
    Page<Barbeiro> findAll(Pageable pageable);

    List<Barbeiro> findByAtivoTrue();

    List<Barbeiro> findAllByOrderByNomeAsc();
}