package br.ifg.urt.barbearia_api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes do Modelo de Usuário")
public class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setup() {
        this.usuario = new Usuario();
    }

    @Test
    @DisplayName("Deve instanciar um usuário e validar seus atributos básicos")
    void deveInstanciarUsuarioComDadosCorretos() {
        // ARRANGE
        Long idEsperado = 10L;
        String nomeEsperado = "Admin Sistema";

        // ACT
        usuario.setId(idEsperado);
        usuario.setNome(nomeEsperado);

        // ASSERT
        assertNotNull(usuario);
        assertEquals(idEsperado, usuario.getId());
        assertEquals(nomeEsperado, usuario.getNome());
    }
}