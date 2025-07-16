package br.com.loja.app.dtos;

import java.time.LocalDate;
import java.util.List;

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
public class ClienteDTO {

    // Descomente se precisar retornar o ID
    private Long clienteId; 

    private String nome;
    private String cpf;

    //Depois colocar como Enum faz mais sentido
    private Character sexo; // 'M' ou 'F'
    private LocalDate dataNascimento; // Formato esperado: AAAA-MM-DD

    private EnderecoDTO endereco; 
    private List<TelefoneDTO> telefones; 

}
