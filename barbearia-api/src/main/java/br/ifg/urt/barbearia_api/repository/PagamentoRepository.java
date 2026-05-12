package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    // Aqui não precisamos de métodos extras por enquanto
}