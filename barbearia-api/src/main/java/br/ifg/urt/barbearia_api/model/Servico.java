package br.ifg.urt.barbearia_api.model;

import jakarta.persistence.Entity;

@Entity
public class Servico extends Item {

    private Integer duracaoMinutos;

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }
}