package br.ifg.urt.barbearia_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "clientes")
public class Cliente extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(length = 255)
    private String observacoes;

    public Cliente() {
    }

    public Cliente(Long id, String nome, String email,
                   String telefone, String senha,
                   String observacoes) {

        super(id, nome, email, telefone, senha);

        this.observacoes = observacoes;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}