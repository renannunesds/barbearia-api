package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    default Item findByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado com ID: " + id));
    }
}