package br.ifg.urt.barbearia_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("API Sistema de Barbearia")
                .version("v1")
                .description("API REST para gerenciamento de barbearia (Barbeiros, Especialidades, Clientes e Agendamentos). " +
                        "Trabalho desenvolvido para a disciplina de Programação para Web.")
                .termsOfService("https://ifgoiano.edu.br/termos")
                .license(apiLicense());
    }

    private License apiLicense() {
        return new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html");
    }
}