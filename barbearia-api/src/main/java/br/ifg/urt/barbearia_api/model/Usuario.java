package br.ifg.urt.barbearia_api.model;

import br.ifg.urt.barbearia_api.model.vo.EmailVO;
import br.ifg.urt.barbearia_api.model.vo.TelefoneVO;
import br.ifg.urt.barbearia_api.model.vo.SenhaVO;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false, length = 100)
    protected String nome;

    @Embedded
    protected EmailVO email;

    @Embedded
    protected TelefoneVO telefone;

    @Embedded
    protected SenhaVO senha;

    public Usuario() {
    }

    public Usuario(Long id, String nome, EmailVO email, TelefoneVO telefone, SenhaVO senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public EmailVO getEmail() { return email; }
    public TelefoneVO getTelefone() { return telefone; }
    public SenhaVO getSenha() { return senha; }

    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(EmailVO email) { this.email = email; }
    public void setTelefone(TelefoneVO telefone) { this.telefone = telefone; }
    public void setSenha(SenhaVO senha) { this.senha = senha; }
}