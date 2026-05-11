package br.ifg.urt.barbearia_api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
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

    public Long getIdItemVenda() {
        return idItemVenda;
    }

    public void setIdItemVenda(Long idItemVenda) {
        this.idItemVenda = idItemVenda;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}