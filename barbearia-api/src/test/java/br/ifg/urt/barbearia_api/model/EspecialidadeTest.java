package br.ifg.urt.barbearia_api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes do Modelo de Especialidade")
public class EspecialidadeTest {

    private Especialidade especialidade;

    @BeforeEach
    void setup() {
        this.especialidade = new Especialidade();
    }

    @Test
    @DisplayName("Deve instanciar uma especialidade e manipular sua descrição")
    void deveInstanciarEspecialidadeComDadosCorretos() {
        // ARRANGE
        Long idEsperado = 5L;
        String nomeEspecialidade = "Corte Degradê";

        // ACT
        especialidade.setId(idEsperado);
        especialidade.setNome(nomeEspecialidade); // Se no seu projeto for 'setDescricao', ajuste aqui

        // ASSERT
        assertNotNull(especialidade);
        assertEquals(idEsperado, especialidade.getId());
        assertEquals(nomeEspecialidade, especialidade.getNome());
    }
}