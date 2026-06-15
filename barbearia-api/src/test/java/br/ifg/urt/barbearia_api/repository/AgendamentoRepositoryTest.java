package br.ifg.urt.barbearia_api.repository;

import br.ifg.urt.barbearia_api.model.Agendamento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // Inicia apenas os componentes do JPA e usa banco em memória
class AgendamentoRepositoryTest {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Test
    @DisplayName("Deve salvar um agendamento com sucesso no banco de dados")
    void deveSalvarAgendamentoComSucesso() {
        // Arrange (Preparação)
        Agendamento agendamento = new Agendamento();
        // preencha aqui os dados obrigatórios do seu modelo de Agendamento, ex:
        // agendamento.setData(LocalDateTime.now());
        // agendamento.setStatus("PENDENTE");

        // Act (Ação)
        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);

        // Assert (Verificação)
        assertThat(agendamentoSalvo).isNotNull();
        assertThat(agendamentoSalvo.getIdAgendamento()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Deve buscar um agendamento por ID com sucesso")
    void deveBuscarPorIdComSucesso() {
        // Arrange
        Agendamento agendamento = new Agendamento();
        Agendamento salvo = agendamentoRepository.save(agendamento);

        // Act
        Optional<Agendamento> encontrado = agendamentoRepository.findById(salvo.getIdAgendamento());

        // Assert
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getIdAgendamento()).isEqualTo(salvo.getIdAgendamento());
    }
}