package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.Especialidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {

    Page<Especialidade> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}