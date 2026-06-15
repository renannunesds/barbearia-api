package br.ifg.urt.barbearia_api.model;

import br.ifg.urt.barbearia_api.model.vo.EmailVO;
import br.ifg.urt.barbearia_api.model.vo.TelefoneVO;
import br.ifg.urt.barbearia_api.model.vo.SenhaVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "barbeiros")
public class Barbeiro extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private Boolean ativo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "barbeiro_servico",
            joinColumns = @JoinColumn(name = "id_barbeiro"),
            inverseJoinColumns = @JoinColumn(name = "id_servico")
    )
    private List<Servico> servicos = new ArrayList<>();

    public Barbeiro() {
    }

    public Barbeiro(Long id, String nome, EmailVO email, TelefoneVO telefone, SenhaVO senha,
                    List<Servico> servicos, Boolean ativo) {
        super(id, nome, email, telefone, senha);
        this.servicos = servicos;
        this.ativo = ativo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public void activarBarbeiro() {
        this.ativo = true;
    }

    public void desativarBarbeiro() {
        this.ativo = false;
    }

    public Boolean estaAtivo() {
        return this.ativo != null && this.ativo;
    }
}