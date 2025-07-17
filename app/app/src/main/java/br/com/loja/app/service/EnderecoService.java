package br.com.loja.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja.app.entity.Endereco;
import br.com.loja.app.exception.CampoNuloException;
import br.com.loja.app.exception.DadosInvalidosException;
import br.com.loja.app.exception.DadosNaoEncontradosException;
import br.com.loja.app.repository.EnderecoRepository;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Boolean verificarEndereco(Endereco endereco) {

        if (endereco == null) { 
           throw new DadosNaoEncontradosException("Endereço não pode ser nulo para validação.");
        }

        // Validação da Rua
        String rua = endereco.getRua();
        if (rua == null || rua.trim().isEmpty()) {
            throw new CampoNuloException("Rua é obrigatória.");
        }
        if (rua.length() > 150) {
            throw new DadosInvalidosException("Rua deve ter no máximo 150 caracteres.");
        }

        // Validação da Cidade
        String cidade = endereco.getCidade();
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new CampoNuloException("Cidade é obrigatória.");
        }
        if (cidade.length() > 50) {
            throw new DadosInvalidosException("Cidade deve ter no máximo 50 caracteres.");
        }
        // Regex para garantir que a cidade não tenha números ou caracteres especiais não permitidos
    
        if (!cidade.matches("^[a-zA-ZáàâãéèêíóôõúüçÁÀÂÃÉÈÊÍÓÔÕÚÜÇ\\s\\-]+$")) {
            throw new DadosInvalidosException(
                    "Nome da cidade inválido. Não deve conter números ou caracteres especiais.");
        }

        // Validação da UF (Estado)
        String uf = endereco.getUf();
        if (uf == null || uf.trim().isEmpty()) {
            throw new CampoNuloException("UF (Estado) é obrigatória.");
        }
        // UF deve ter exatamente 2 letras maiúsculas
        if (!uf.matches("^[A-Z]{2}$")) {
            throw new DadosInvalidosException("UF (Estado) inválida. Deve conter 2 letras maiúsculas.");
        }

        // Validação do Número
        String numero = endereco.getNumero();
        if (numero == null || numero.trim().isEmpty()) {
            throw new CampoNuloException("Número é obrigatório.");
        }
        if (numero.length() > 10) {
            throw new DadosInvalidosException("Número deve ter no máximo 10 caracteres.");
        }

        // Validação do Complemento (opcional, pode ser nulo ou vazio se permitido)
        String complemento = endereco.getComplemento();
      
        if (complemento != null && complemento.length() > 100) {
            throw new DadosInvalidosException("Complemento deve ter no máximo 100 caracteres.");
        }

        // Validação do CEP
        String cep = endereco.getCep();
        if (cep == null || cep.trim().isEmpty()) {
            throw new CampoNuloException("CEP é obrigatório.");
        }
        // Remove qualquer caractere que não seja dígito para a validação
        String cepNumerico = cep.replaceAll("[^0-9]", "");
        // Regex para CEP: 8 dígitos numéricos (permite formato 00000-000 ou 00000000)
        if (!cepNumerico.matches("\\d{8}")) {
            throw new DadosInvalidosException("CEP inválido. Deve conter apenas 8 dígitos numéricos.");
        }

        // Validação do Bairro
        String bairro = endereco.getBairro();
        if (bairro == null || bairro.trim().isEmpty()) {
            throw new CampoNuloException("Bairro é obrigatório.");
        }
        if (bairro.length() > 50) {
            throw new DadosInvalidosException("Bairro deve ter no máximo 50 caracteres.");
        }

        // Se todas as validações passarem
        return true;
    }
}
