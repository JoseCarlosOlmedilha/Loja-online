package br.com.loja.app.dtos;

import br.com.loja.app.entity.enume.Uf;
import br.com.loja.app.validation.NotEmptyUF;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {

    //private Long enderecoId;

    @NotBlank(message = "Rua não pode ser vazia")
    private String rua;

    @NotBlank(message = "Número não pode ser vazio")
    private String numero;

    private String complemento;

    @NotBlank(message = "Bairro não pode ser vazio")
    private String bairro;

    @NotBlank(message = "Cidade não pode ser vazia")
    private String cidade;

    @NotEmptyUF(message = "UF não pode ser vazio nem nulo")
    private Uf uf;

    @NotBlank(message = "CEP não pode ser vazio")
    private String cep;
}

