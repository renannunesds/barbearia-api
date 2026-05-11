package br.ifg.urt.barbearia_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

    @Entity
    @Inheritance(strategy = InheritanceType.JOINED)
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public abstract class Item {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idItem;

        private String nome;

        private String descricao;

        private BigDecimal valor;
    }


