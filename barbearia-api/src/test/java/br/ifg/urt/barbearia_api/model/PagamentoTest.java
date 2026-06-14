package br.ifg.urt.barbearia_api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes do Modelo de Pagamento")
public class PagamentoTest {

    private Pagamento pagamento;

    @BeforeEach
    void setup() {
        // Inicializa o objeto antes de cada teste
        this.pagamento = new Pagamento();
    }

    @Test
    @DisplayName("Deve instanciar um pagamento e manipular seus atributos corretamente")
    void deveInstanciarPagamentoComDadosCorretos() {
        // 1. ARRANGE
        Long idEsperado = 1L;
        String formaPagamentoEsperada = "PIX";

        // 2. ACT
        pagamento.setIdPagamento(idEsperado);
        pagamento.setFormaPagamento(formaPagamentoEsperada);

        // Simulando a regra interna da entidade de confirmar o pagamento
        pagamento.confirmarPagamento(); // Esse método deve setar o status para "CONCLUIDO" internamente

        // 3. ASSERT
        assertNotNull(pagamento, "O objeto pagamento não deveria ser nulo");
        assertEquals(idEsperado, pagamento.getIdPagamento());
        assertEquals(formaPagamentoEsperada, pagamento.getFormaPagamento());
        // Verifica se a regra simples do modelo funcionou
        assertEquals("CONCLUIDO", pagamento.getStatus());
    }
}