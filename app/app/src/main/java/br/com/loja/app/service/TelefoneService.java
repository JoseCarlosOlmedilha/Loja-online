package br.com.loja.app.service;

import org.springframework.stereotype.Service;
import br.com.loja.app.dtos.TelefoneDTO;
import br.com.loja.app.entity.Telefone;
import br.com.loja.app.repository.TelefoneRepository;


@Service
public class TelefoneService {

    private TelefoneRepository telefoneRepository;

    public TelefoneService(TelefoneRepository telefoneRepository) {
        this.telefoneRepository = telefoneRepository;
    }

    protected Telefone fromTelefoneDTO(TelefoneDTO dto) {
        Telefone telefone = new Telefone();
        telefone.setTipo(dto.getTipo());
        telefone.setNumero(dto.getNumero());
        telefone.setDdd(dto.getDdd());
        return telefone;
    }

    protected  TelefoneDTO toTelefoneDTO(Telefone telefone) {
        TelefoneDTO dto = new TelefoneDTO();
        dto.setTipo(telefone.getTipo());
        dto.setNumero(telefone.getNumero());
        dto.setDdd(telefone.getDdd());
        return dto;
    }

}