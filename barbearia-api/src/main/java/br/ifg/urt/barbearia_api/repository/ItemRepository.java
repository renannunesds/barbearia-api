package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
