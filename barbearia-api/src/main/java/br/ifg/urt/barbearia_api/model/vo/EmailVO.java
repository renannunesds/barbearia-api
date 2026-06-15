package br.ifg.urt.barbearia_api.model.vo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

@Embeddable
public record EmailVO(
        @Column(name = "usuario_email", nullable = false)
        String endereco
) {

    public EmailVO {
        if (endereco == null || !endereco.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Formato de e-mail inválido.");
        }
    }

    public String getEnderecoEmMinusculo() {
        return endereco != null ? endereco.toLowerCase().trim() : null;
    }

    public String getProvedor() {
        if (endereco == null || !endereco.contains("@")) return "";
        return endereco.substring(endereco.indexOf("@") + 1);
    }
}