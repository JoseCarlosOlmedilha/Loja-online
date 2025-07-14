package br.com.loja.app.service;

import java.util.List;

import org.springframework.stereotype.Service;


import br.com.loja.app.entity.Telefone;
import br.com.loja.app.exception.CampoNuloException;
import br.com.loja.app.exception.DadosInvalidosException;


@Service
public class TelefoneService {

public Boolean verificarTelefones(List<Telefone> telefones){
    
    if(telefones == null){
        throw new CampoNuloException("Telefone não pode ser nulo");
    }

    if (telefones.isEmpty()) {
        throw new DadosInvalidosException("A lista de telefones não pode estar vazia.");
    }


    for(Telefone telefone : telefones){
    
        if (telefone == null) {
            throw new CampoNuloException("Um objeto Telefone na lista não pode ser nulo.");
        }
        
        if (telefone.getNumero() == null) {
                throw new CampoNuloException("O número do telefone não pode ser nulo.");
        }

        String numeroProcessado = telefone.getNumero().trim();

        String numeroLimpo = numeroProcessado.replaceAll("[^0-9]", "");
    
        if (telefone.getDdd() == null) {
                throw new CampoNuloException("O DDD do telefone não pode ser nulo.");
        }

        String dddProcessado = telefone.getDdd().trim();
        String dddLimpo = dddProcessado.replaceAll("[^0-9]", "");

        if (numeroLimpo.isEmpty()) {
                throw new DadosInvalidosException("O número do telefone não pode estar vazio ou conter apenas caracteres inválidos.");
        }

        if (numeroLimpo.length() != 8 && numeroLimpo.length() != 9) {
                throw new DadosInvalidosException("Número de telefone deve ter 8 ou 9 dígitos.");
        }
        
        if (dddLimpo.isEmpty()) {
                throw new DadosInvalidosException("O DDD do telefone não pode estar vazio ou conter apenas caracteres inválidos.");
            }

        if (dddLimpo.length() != 2) {
            throw new DadosInvalidosException("DDD do telefone deve ter 2 dígitos.");
        }
    }
    return true;
}

}
