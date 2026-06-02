package br.ifg.urt.barbearia_api.model.vo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

@Embeddable
public record SenhaVO(
        @Column(name = "usuario_senha", nullable = false)
        String valor
) {
    public SenhaVO {
        if (valor == null || valor.length() < 6) {
            throw new IllegalArgumentException("A senha deve conter no mínimo 6 caracteres.");
        }
    }
}