package br.com.loja.app.dtos;

import java.time.LocalDate;
import java.util.List;
import org.hibernate.validator.constraints.br.CPF;
import br.com.loja.app.entity.enume.Sexo;
import br.com.loja.app.validation.NotEmptyEndereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    //private Long clienteId; 

    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;

    @NotBlank(message = "CPF não pode ser vazio")
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotNull(message = "Sexo não pode ser nulo")
    private Sexo sexo;

    @Past(message = "Data de nascimento deve ser uma data passada")
    private LocalDate dataNascimento;

    @NotEmptyEndereco
    private EnderecoDTO endereco;

    @NotNull(message = "Telefones não podem ser nulos")
    private List<TelefoneDTO> telefones;

}
