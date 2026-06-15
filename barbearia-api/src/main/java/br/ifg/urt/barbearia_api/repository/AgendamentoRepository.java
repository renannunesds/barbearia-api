package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.Agendamento;
import br.ifg.urt.barbearia_api.model.Barbeiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    // Adicionado apenas este método para o Service voltar a compilar
    default Agendamento findByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado com ID: " + id));
    }

    @Query("SELECT COUNT(a) > 0 FROM Agendamento a WHERE a.barbeiro = :barbeiro AND a.data = :data AND a.horario = :horario")
    boolean existsByBarbeiroAndDataAndHorario(
            @Param("barbeiro") Barbeiro barbeiro,
            @Param("data") LocalDate data,
            @Param("horario") LocalTime horario
    );
}