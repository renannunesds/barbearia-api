package br.ifg.urt.barbearia_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "barbeiros")
public class Barbeiro extends Usuario {

    @Column(nullable = false, length = 100)
    private String especialidade;

    @Column(nullable = false)
    private Boolean ativo;

    public Barbeiro() {
    }

    public Barbeiro(Long id, String nome, String email, String telefone, String senha,
                    String especialidade, Boolean ativo) {
        super(id, nome, email, telefone, senha);
        this.especialidade = especialidade;
        this.ativo = ativo;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public void ativarBarbeiro() {
        this.ativo = true;
    }

    public void desativarBarbeiro() {
        this.ativo = false;
    }
}