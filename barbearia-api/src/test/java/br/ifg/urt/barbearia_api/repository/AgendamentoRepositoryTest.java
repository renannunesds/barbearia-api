package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.Agendamento;
import br.ifg.urt.barbearia_api.mother.AgendamentoMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AgendamentoRepositoryTest {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Test
    @DisplayName("Deve salvar um agendamento com sucesso no banco de dados")
    void deveSalvarAgendamentoComSucesso() {
        Agendamento agendamento = AgendamentoMother.padrao();
        agendamento.setIdAgendamento(null);

        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);

        assertThat(agendamentoSalvo).isNotNull();
        assertThat(agendamentoSalvo.getIdAgendamento()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Deve buscar um agendamento por ID com sucesso")
    void deveBuscarPorIdComSucesso() {
        Agendamento agendamento = AgendamentoMother.padrao();
        agendamento.setIdAgendamento(null);
        Agendamento salvo = agendamentoRepository.save(agendamento);

        Optional<Agendamento> encontrado = agendamentoRepository.findById(salvo.getIdAgendamento());

        assertThat(encontrado).isPresent();
    }
}