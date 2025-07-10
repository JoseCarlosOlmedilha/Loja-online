package br.com.loja.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import br.com.loja.app.entity.Telefone;
import br.com.loja.app.repository.TelefoneRepository;

@Service
public class TelefoneService {

@Autowired
private TelefoneRepository telefoneRepository;


    public boolean cadastrarTelefone(Telefone telefone){

        telefoneRepository.save(telefone);

        return true;

    }

}
