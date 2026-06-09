package br.ifg.urt.barbearia_api.model;

import br.ifg.urt.barbearia_api.model.vo.EmailVO;
import br.ifg.urt.barbearia_api.model.vo.TelefoneVO;
import br.ifg.urt.barbearia_api.model.vo.SenhaVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "barbeiros")
public class Barbeiro extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private Boolean ativo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "barbeiro_especialidade",
            joinColumns = @JoinColumn(name = "id_barbeiro"),
            inverseJoinColumns = @JoinColumn(name = "id_especialidade")
    )
    private List<Especialidade> especialidades = new ArrayList<>();

    public Barbeiro() {
    }

    public Barbeiro(Long id, String nome, EmailVO email, TelefoneVO telefone, SenhaVO senha,
                    List<Especialidade> especialidades, Boolean ativo) {
        super(id, nome, email, telefone, senha);
        this.especialidades = especialidades;
        this.ativo = ativo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<Especialidade> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidade> especialidades) {
        this.especialidades = especialidades;
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