package br.com.loja.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {


 // Não incluímos o 'enderecoId' aqui para o DTO de criação/entrada.
    private Long enderecoId; // Descomente se precisar retornar o ID

    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
}

