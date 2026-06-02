package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    default Cliente findByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));
    }

    Optional<Cliente> findByEmailEndereco(String email);

    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    List<Cliente> findAllByOrderByNomeAsc();
}