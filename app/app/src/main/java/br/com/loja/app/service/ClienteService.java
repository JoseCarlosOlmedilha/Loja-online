package br.com.loja.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja.app.entity.Cliente;
import br.com.loja.app.repository.ClienteRepository;

@Service
public class ClienteService {

@Autowired
private ClienteRepository clienteRepository;


public Cliente cadastrarCliente(Cliente cliente){

    Cliente clienteSalvo = clienteRepository.save(cliente);

    return clienteSalvo;
}

}
