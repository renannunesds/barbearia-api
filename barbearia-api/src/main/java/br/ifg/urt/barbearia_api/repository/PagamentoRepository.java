package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    default Pagamento findByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado com ID: " + id));
    }
}