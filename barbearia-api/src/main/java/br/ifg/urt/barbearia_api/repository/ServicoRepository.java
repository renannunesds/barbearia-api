package br.ifg.urt.barbearia_api.repository;


import br.ifg.urt.barbearia_api.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
}
