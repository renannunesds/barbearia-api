package br.ifg.urt.barbearia_api.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgendamento;

    private LocalDate data;
    private LocalTime horario;
    private String status; // Ex: PENDENTE, CONCLUIDO, CANCELADO

    // Relacionamento com Cliente (conforme seu diagrama)
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // Relacionamento com Barbeiro (conforme seu diagrama)
    @ManyToOne
    @JoinColumn(name = "barbeiro_id")
    private Barbeiro barbeiro;

    // Relacionamento com Servico (Estava faltando na sua print!)
    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;

    // Relacionamento 1 para 1 com Pagamento
    // O 'mappedBy' indica que o mapeamento principal está na classe Pagamento
    @OneToOne(mappedBy = "agendamento", cascade = CascadeType.ALL)
    private Pagamento pagamento;

    // --- CONSTRUTORES ---
    public Agendamento() {
    }

    // --- GETTERS E SETTERS (Essenciais para o Spring funcionar) ---

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

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
}