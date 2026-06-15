package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    default Produto findByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
    }

    Page<Produto> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    List<Produto> findAllByOrderByNomeAsc();
}