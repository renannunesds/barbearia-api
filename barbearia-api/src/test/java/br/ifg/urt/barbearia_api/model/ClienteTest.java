package br.ifg.urt.barbearia_api.model;

import br.ifg.urt.barbearia_api.model.vo.TelefoneVO;
import br.ifg.urt.barbearia_api.model.vo.EmailVO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes do Modelo de Cliente")
public class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    void setup() {
        // Inicializa o objeto antes de cada teste
        this.cliente = new Cliente();
    }

    @Test
    @DisplayName("Deve instanciar um cliente e manipular seus atributos corretamente")
    void deveInstanciarClienteComDadosCorretos() {
        // 1. ARRANGE
        Long idEsperado = 1L;
        String nomeEsperado = "Renan Nunes";
        TelefoneVO telefoneEsperado = new TelefoneVO("62999999999");
        EmailVO emailEsperado = new EmailVO("renan@email.com");

        // 2. ACT
        cliente.setId(idEsperado);
        cliente.setNome(nomeEsperado);
        cliente.setTelefone(telefoneEsperado);
        cliente.setEmail(emailEsperado);

        // 3. ASSERT
        assertNotNull(cliente, "O objeto cliente não deveria ser nulo");
        assertEquals(idEsperado, cliente.getId());
        assertEquals(nomeEsperado, cliente.getNome());
        assertEquals(telefoneEsperado, cliente.getTelefone());
        assertEquals(emailEsperado, cliente.getEmail());
    }
}