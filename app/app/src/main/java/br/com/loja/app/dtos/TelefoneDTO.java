package br.com.loja.app.dtos;

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

    // Descomente se precisar retornar o ID
    private Long telefoneId;


    //Colocar como Enum
    private String tipo;   // Ex: "CEL", "RES", "COM"
    private String numero;
    private String ddd;
}

