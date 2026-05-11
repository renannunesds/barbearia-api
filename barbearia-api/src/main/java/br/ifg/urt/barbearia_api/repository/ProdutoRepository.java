package br.ifg.urt.barbearia_api.repository;


import br.ifg.urt.barbearia_api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
