package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Buscar cliente por ID ou lançar exceção
    default Cliente findByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cliente não encontrado com ID: " + id));
    }

    // Buscar por email
    Optional<Cliente> findByEmail(String email);

    // Buscar por nome
    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    // Ordenar por nome
    List<Cliente> findAllByOrderByNomeAsc();
}