package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.Agendamento;
import br.ifg.urt.barbearia_api.model.Barbeiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    // Esta é a lógica mágica que evita o erro de dois clientes no mesmo horário
    boolean existsByBarbeiroAndDataAndHorario(Barbeiro barbeiro, LocalDate data, LocalTime horario);
}