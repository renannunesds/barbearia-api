package br.ifg.urt.barbearia_api.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pagamentos")
public class Pagamento implements Serializable {

    // 1. Padronização com o modelo do projeto
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPagamento;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private LocalDate dataPagamento;

    @Column(nullable = false, length = 50)
    private String formaPagamento;

    @Column(nullable = false, length = 50)
    private String status;

    @OneToOne
    @JoinColumn(name = "agendamento_id", nullable = false)
    private Agendamento agendamento;

    // Construtor padrão obrigatório para o JPA
    public Pagamento() {
    }

    // 2. Construtor completo adicionado (Mantendo o padrão oficial do projeto)
    public Pagamento(Long idPagamento, BigDecimal valorTotal, LocalDate dataPagamento,
                     String formaPagamento, String status, Agendamento agendamento) {
        this.idPagamento = idPagamento;
        this.valorTotal = valorTotal;
        this.dataPagamento = dataPagamento;
        this.formaPagamento = formaPagamento;
        this.status = status;
        this.agendamento = agendamento;
    }

    // Getters e Setters
    public Long getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Long idPagamento) {
        this.idPagamento = idPagamento;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    // 3. Métodos de negócio adicionados (Facilitará muito a lógica do seu PagamentoService)
    public void confirmarPagamento() {
        this.status = "PAGO";
    }

    public void estornarPagamento() {
        this.status = "ESTORNADO";
    }
}