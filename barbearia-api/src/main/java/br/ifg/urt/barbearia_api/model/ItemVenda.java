package br.ifg.urt.barbearia_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItemVenda;

    @ManyToOne
    @JoinColumn(name = "id_item")
    private Item item;

    private Integer quantidade;

    private BigDecimal valorUnitario;

    private BigDecimal subtotal;
}
