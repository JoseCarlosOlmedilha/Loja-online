package br.com.loja.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.loja.app.entity.Cliente;
import br.com.loja.app.service.ClienteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/cliente")
public class ClinteController {

    @Autowired
    private ClienteService clienteService;


    
    @PostMapping("/cadastrar")
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente obj){

       Cliente clienteSalvo = clienteService.cadastrarCliente(obj);

        return new ResponseEntity<>(clienteSalvo, HttpStatus.OK);
    }
}
