package br.ifg.urt.barbearia_api.model;

import jakarta.persistence.Entity;

@Entity
public class Produto extends Item {

    private Integer quantidadeEstoque;

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
}