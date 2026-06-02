package br.ifg.urt.barbearia_api.model;

import br.ifg.urt.barbearia_api.model.vo.EmailVO;
import br.ifg.urt.barbearia_api.model.vo.TelefoneVO;
import br.ifg.urt.barbearia_api.model.vo.SenhaVO;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "barbeiros")
public class Barbeiro extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 100)
    private String especialidade;

    @Column(nullable = false)
    private Boolean ativo;

    public Barbeiro() {
    }

    public Barbeiro(Long id, String nome, EmailVO email, TelefoneVO telefone, SenhaVO senha,
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