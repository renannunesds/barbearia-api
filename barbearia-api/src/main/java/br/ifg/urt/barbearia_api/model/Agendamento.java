package br.ifg.urt.barbearia_api.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "agendamentos")
public class Agendamento implements Serializable {

    // 1. Padronização com o modelo do seu colega
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgendamento;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime horario;

    @Column(nullable = false, length = 50)
    private String status;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "barbeiro_id", nullable = false)
    private Barbeiro barbeiro;

    @ManyToOne
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    @OneToOne(mappedBy = "agendamento", cascade = CascadeType.ALL)
    private Pagamento pagamento;

    // Construtor padrão obrigatório para o JPA
    public Agendamento() {
    }

    // 2. Construtor completo adicionado (Igual ao padrão do seu colega)
    public Agendamento(Long idAgendamento, LocalDate data, LocalTime horario, String status,
                       Cliente cliente, Barbeiro barbeiro, Servico servico, Pagamento pagamento) {
        this.idAgendamento = idAgendamento;
        this.data = data;
        this.horario = horario;
        this.status = status;
        this.cliente = cliente;
        this.barbeiro = barbeiro;
        this.servico = servico;
        this.pagamento = pagamento;
    }

    // Getters e Setters
    public Long getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(Long idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Barbeiro getBarbeiro() {
        return barbeiro;
    }

    public void setBarbeiro(Barbeiro barbeiro) {
        this.barbeiro = barbeiro;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    // 3. Método auxiliar de segurança (Evita que o JPA duplique registros ou crie dados órfãos)
    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
        if (pagamento != null && pagamento.getAgendamento() != this) {
            pagamento.setAgendamento(this);
        }
    }

    // 4. Métodos de negócio adicionados (Seguindo as boas práticas do seu colega)
    public void cancelarAgendamento() {
        this.status = "CANCELADO";
    }

    public void confirmarAgendamento() {
        this.status = "CONFIRMADO";
    }
}