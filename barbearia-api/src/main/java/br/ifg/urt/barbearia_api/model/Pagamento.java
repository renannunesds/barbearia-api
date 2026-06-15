package br.ifg.urt.barbearia_api.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pagamentos")
public class Pagamento implements Serializable {

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private StatusPagamento status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agendamento_id", nullable = false)
    private Agendamento agendamento;

    public Pagamento() {
    }

    public Pagamento(Long idPagamento, BigDecimal valorTotal, LocalDate dataPagamento,
                     String formaPagamento, StatusPagamento status, Agendamento agendamento) {
        this.idPagamento = idPagamento;
        this.valorTotal = valorTotal;
        this.dataPagamento = dataPagamento;
        this.formaPagamento = formaPagamento;
        this.status = status;
        this.agendamento = agendamento;
    }

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

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    public void confirmarPagamento() {
        this.status = StatusPagamento.PAGO;
    }

    public void estornarPagamento() {
        this.status = StatusPagamento.ESTORNADO;
    }
}