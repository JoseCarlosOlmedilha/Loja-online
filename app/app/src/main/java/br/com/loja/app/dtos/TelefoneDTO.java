package br.com.loja.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TelefoneDTO {

    // Não incluímos o 'telefoneId' aqui para o DTO de criação/entrada,
    // pois ele é um ID gerado pelo banco de dados.
    // Se este DTO também fosse usado para retornar dados (GET), você poderia incluí-lo.
    private Long telefoneId;

    private String tipo;   // Ex: "CEL", "RES", "COM"
    private String numero;
    private String ddd;
}

