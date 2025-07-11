package br.com.loja.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.loja.app.entity.Cliente;
import br.com.loja.app.entity.Endereco;
import br.com.loja.app.entity.Telefone;
import br.com.loja.app.repository.ClienteRepository;


@Service
public class ClienteService {

@Autowired
private ClienteRepository clienteRepository;


public Cliente cadastrarCliente(Cliente cliente){
    return null;
}

public Cliente excluirCliente(Cliente cliente){
    return null;
}

public Cliente buscarCliente(Cliente cliente){
    return null;

}

private Boolean verificarTelefones(List<Telefone> telefones){
    return null;
}

private Boolean verificarEndereco(Endereco endereco){
    if(endereco == null){
        throw new IllegalArgumentException("Endereço não pode ser nulo");
    }

    return true;
}

private Boolean verificarCpf(Cliente cliente){
    return null;
}

private Boolean verificaSexo(Cliente cliente){
    Character sexo = cliente.getSexo();

    if(sexo != 'M' && sexo != 'F' ){
        throw new IllegalArgumentException("Sexo inválido, o valor de sexo tem que ser M ou F");
    }

    return true;

}



}

