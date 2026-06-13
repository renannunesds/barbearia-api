package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

    default Servico findByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com ID: " + id));
    }

    Page<Servico> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    List<Servico> findAllByOrderByNomeAsc();
}