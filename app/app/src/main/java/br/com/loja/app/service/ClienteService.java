package br.com.loja.app.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.loja.app.dtos.ClienteDTO;
import br.com.loja.app.dtos.EnderecoDTO;
import br.com.loja.app.dtos.TelefoneDTO;
import br.com.loja.app.entity.Cliente;
import br.com.loja.app.entity.Endereco;
import br.com.loja.app.entity.Telefone;
import br.com.loja.app.exception.DadosInvalidosException;
import br.com.loja.app.exception.DadosNaoEncontradosException;
import br.com.loja.app.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoService enderecoService;
    private final TelefoneService telefoneService;

    public ClienteService(ClienteRepository clienteRepository, EnderecoService enderecoService, TelefoneService telefoneService) {
        this.clienteRepository = clienteRepository;
        this.enderecoService = enderecoService;
        this.telefoneService = telefoneService;
    }

    @Transactional
    public ClienteDTO cadastrarCliente(ClienteDTO clienteDTO) {

        clienteRepository.findByCpf(clienteDTO.getCpf()).ifPresent(clienteEncontrado -> {
            throw new DadosInvalidosException("CPF já cadastrado.");
        });

        Cliente cliente = fromClienteDTO(clienteDTO); 
        Endereco endereco = enderecoService.fromEnderecoDTO(clienteDTO.getEndereco());
        endereco.setCliente(cliente);
        cliente.setEndereco(endereco);

        if (clienteDTO.getTelefones() != null && !clienteDTO.getTelefones().isEmpty()) {
            List<Telefone> telefones = clienteDTO.getTelefones().stream()
                .map(telefoneDTO -> {
                    Telefone telefone = telefoneService.fromTelefoneDTO(telefoneDTO);
                    telefone.setCliente(cliente);
                    return telefone;
                })
                .collect(Collectors.toList());
            cliente.setTelefones(telefones);
        } else {
            cliente.setTelefones(new java.util.ArrayList<>());
        }

        Cliente clienteSalvo = clienteRepository.save(cliente);

        return toClienteDTO(clienteSalvo);
    }

    public ClienteDTO buscarClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new DadosNaoEncontradosException("Cliente com ID " + id + " não encontrado."));
        return toClienteDTO(cliente);
    }

    public void excluirCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new DadosNaoEncontradosException("Cliente com ID " + clienteId + " não encontrado para exclusão.");
        }
        clienteRepository.deleteById(clienteId);
    }

    private Cliente fromClienteDTO(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setSexo(dto.getSexo());
        cliente.setDataNascimento(dto.getDataNascimento());
        return cliente;
    }


    private ClienteDTO toClienteDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setNome(cliente.getNome());
        dto.setCpf(cliente.getCpf());
        dto.setSexo(cliente.getSexo());
        dto.setDataNascimento(cliente.getDataNascimento());

        if (cliente.getEndereco() != null) {
            dto.setEndereco(enderecoService.toEnderecoDTO(cliente.getEndereco()));
        }

        if (cliente.getTelefones() != null && !cliente.getTelefones().isEmpty()) {
            dto.setTelefones(cliente.getTelefones().stream()
                .map(this::toTelefoneDTO)
                .collect(Collectors.toList()));
        }
        return dto;
    }

    private TelefoneDTO toTelefoneDTO(Telefone telefone) {
        TelefoneDTO dto = new TelefoneDTO();
        dto.setTipo(telefone.getTipo());
        dto.setNumero(telefone.getNumero());
        dto.setDdd(telefone.getDdd());
        return dto;
    }

    // Métodos de serviço
    public List<ClienteDTO> listarTodosClientesDTO() {
        List<Cliente> clientes = clienteRepository.buscarTodosComRelacionamentos();
        return clientes.stream()
            .map(this::toClienteDTO)
            .collect(Collectors.toList());
    }

    public ClienteDTO atualizarCliente(Long id, ClienteDTO clienteDTO) {
        Cliente clienteExistente = clienteRepository.findById(id)
            .orElseThrow(() -> new DadosNaoEncontradosException("Cliente não encontrado"));

        clienteExistente.setNome(clienteDTO.getNome());
        clienteExistente.setSexo(clienteDTO.getSexo());
        clienteExistente.setDataNascimento(clienteDTO.getDataNascimento());

        if (clienteDTO.getEndereco() != null) {
            if (clienteExistente.getEndereco() == null) {
                clienteExistente.setEndereco(new Endereco());
            }
            Endereco endereco = clienteExistente.getEndereco();
            EnderecoDTO enderecoDTO = clienteDTO.getEndereco();
            endereco.setRua(enderecoDTO.getRua());
            endereco.setNumero(enderecoDTO.getNumero());
            endereco.setComplemento(enderecoDTO.getComplemento());
            endereco.setBairro(enderecoDTO.getBairro());
            endereco.setCidade(enderecoDTO.getCidade());
            endereco.setUf(enderecoDTO.getUf());
            endereco.setCep(enderecoDTO.getCep());
        }

        // Atualização dos telefones
        if (clienteDTO.getTelefones() != null) {
 

        }

        Cliente clienteAtualizado = clienteRepository.save(clienteExistente);
        return toClienteDTO(clienteAtualizado);
    }
}
