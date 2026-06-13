package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {

    default ItemVenda findByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new RuntimeException("Item de venda não encontrado com ID: " + id));
    }
}