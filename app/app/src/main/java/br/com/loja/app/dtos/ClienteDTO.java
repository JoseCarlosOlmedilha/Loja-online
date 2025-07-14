package br.com.loja.app.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    //private Long clienteId; // Descomente se precisar retornar o ID

    private String nome;
    private String cpf;
    private Character sexo; // 'M' ou 'F'
    private LocalDate dataNascimento; // Formato esperado: AAAA-MM-DD

    private EnderecoDTO endereco; // O DTO de Endereço que criamos
    private List<TelefoneDTO> telefones; // Uma lista de DTOs de Telefone

}
