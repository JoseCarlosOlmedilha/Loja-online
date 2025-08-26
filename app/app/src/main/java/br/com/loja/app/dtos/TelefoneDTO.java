package br.com.loja.app.dtos;

import br.com.loja.app.entity.enume.TipoTelefone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO (Data Transfer Object) é uma classe usada para enviar e receber dados entre as partes do sistema.
// Ela serve para transportar apenas as informações necessárias, escondendo dados que o usuário não precisa ver.
// Isso ajuda a manter a segurança e a organização do código.

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TelefoneDTO {

    //private Long telefoneId;

    @NotNull(message = "Tipo não pode ser nulo")
    private TipoTelefone tipo;

    @NotBlank(message = "Número não pode ser vazio")
    private String numero;

    @NotBlank(message = "DDD não pode ser vazio")
    private String ddd;
}

