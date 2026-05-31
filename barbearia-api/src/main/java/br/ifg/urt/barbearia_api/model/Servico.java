package br.ifg.urt.barbearia_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "servicos")
public class Servico extends Item {

    @Column(nullable = false)
    private Integer duracaoMinutos;

    public Servico() {
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }
}