package br.ifg.urt.barbearia_api.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity // Indica que esta classe é uma tabela no banco de dados
@Table(name = "barbeiros") // Nome da tabela
public class Barbeiro extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 100)
    private String especialidade;

    @Column(nullable = false)
    private Boolean ativo;

    // Construtor padrão obrigatório para o JPA
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

    // Método de negócio
    public void ativarBarbeiro() {
        this.ativo = true;
    }

    // Método de negócio
    public void desativarBarbeiro() {
        this.ativo = false;
    }

    // Método auxiliar
    public Boolean estaAtivo() {
        return this.ativo != null && this.ativo;
    }
}