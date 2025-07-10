package br.com.loja.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja.app.entity.Endereco;
import br.com.loja.app.repository.EnderecoRepository;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public boolean cadastrarEndereco(Endereco endereco){

        enderecoRepository.save(endereco);

        return true;

    }
}
