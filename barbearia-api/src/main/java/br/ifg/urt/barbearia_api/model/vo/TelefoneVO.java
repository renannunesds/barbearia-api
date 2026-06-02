package br.ifg.urt.barbearia_api.model.vo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

@Embeddable
public record TelefoneVO(
        @Column(name = "usuario_telefone", length = 15)
        String numero
) {
    public TelefoneVO {
        if (numero != null) {
            String apenasNumeros = numero.replaceAll("\\D", "");
            if (apenasNumeros.length() < 10 || apenasNumeros.length() > 11) {
                throw new IllegalArgumentException("Telefone deve conter DDD e número válido.");
            }
        }
    }

    public String getFormatado() {
        if (numero == null) return "";
        String digitos = numero.replaceAll("\\D", "");
        if (digitos.length() == 11) {
            return String.format("(%s) %s-%s", digitos.substring(0, 2), digitos.substring(2, 7), digitos.substring(7));
        }
        return String.format("(%s) %s-%s", digitos.substring(0, 2), digitos.substring(2, 6), digitos.substring(6));
    }
}