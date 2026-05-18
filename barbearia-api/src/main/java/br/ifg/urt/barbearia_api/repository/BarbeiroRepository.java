package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.Barbeiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarbeiroRepository extends JpaRepository<Barbeiro, Long> {

}