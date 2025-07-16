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
import br.com.loja.app.exception.CampoNuloException;
import br.com.loja.app.exception.DadosInvalidosException;
import br.com.loja.app.exception.DadosNaoEncontradosException;
import br.com.loja.app.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoService enderecoService; // Injetamos o EnderecoService

    // Injeção de dependências via construtor (melhor prática que @Autowired em campo)
    public ClienteService(ClienteRepository clienteRepository, EnderecoService enderecoService) {
        this.clienteRepository = clienteRepository;
        this.enderecoService = enderecoService;
    }

    // --- MÉTODOS DE NEGÓCIO ---

    @Transactional // Garante que a operação seja atômica (tudo ou nada)
    public ClienteDTO cadastrarCliente(ClienteDTO clienteDTO) {
        // 1. Validações iniciais (antes de mapear para entidade)
        validarClienteDTO(clienteDTO); // Chamando um novo método para validações gerais do DTO

        // Opcional: Verifique se o CPF já existe no banco
        clienteRepository.findByCpf(clienteDTO.getCpf()).ifPresent(c -> {
            throw new DadosInvalidosException("CPF já cadastrado.");
        });

        // 2. Mapear DTO para Entidade Cliente
        Cliente cliente = fromClienteDTO(clienteDTO);

        // 3. Mapear e Validar Endereco
        if (clienteDTO.getEndereco() != null) {
            Endereco endereco = fromEnderecoDTO(clienteDTO.getEndereco());
            endereco.setCliente(cliente); // Associa o endereço ao cliente
            cliente.setEndereco(endereco); // Associa o cliente ao endereço

            // Chamar a validação específica do endereço no EnderecoService
            enderecoService.verificarEndereco(endereco);
        } else {
            throw new CampoNuloException("Endereço é obrigatório.");
        }

        // 4. Mapear Telefones
        if (clienteDTO.getTelefones() != null && !clienteDTO.getTelefones().isEmpty()) {
            List<Telefone> telefones = clienteDTO.getTelefones().stream()
                .map(telefoneDTO -> {
                    Telefone telefone = fromTelefoneDTO(telefoneDTO);
                    telefone.setCliente(cliente); // Associa o telefone ao cliente
                    return telefone;
                })
                .collect(Collectors.toList());
            cliente.setTelefones(telefones);
        } else {
            cliente.setTelefones(new java.util.ArrayList<>()); // Garante lista vazia se não houver telefones
        }

        // 5. Salvar no Banco de Dados (cascade = ALL nas associações de Cliente vai salvar endereço e telefones)
        Cliente clienteSalvo = clienteRepository.save(cliente);

        // 6. Mapear a Entidade Salva de volta para DTO de Retorno
        return toClienteDTO(clienteSalvo);
    }

    // Método para buscar cliente (exemplo de como usar DadosNaoEncontradosException)
    public ClienteDTO buscarClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new DadosNaoEncontradosException("Cliente com ID " + id + " não encontrado."));
        return toClienteDTO(cliente);
    }


    // Os métodos cadastrarCliente, excluirCliente e buscarCliente originais
    // podem ser adaptados para usar DTOs ou removidos/renomeados
    // para algo como 'excluirClienteEntity' se for manipular a entidade diretamente.
    // Para uma API REST, normalmente você operaria com DTOs aqui.
    // O método 'cadastrarCliente(Cliente cliente)' original foi adaptado acima.
    public void excluirCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new DadosNaoEncontradosException("Cliente com ID " + clienteId + " não encontrado para exclusão.");
        }
        clienteRepository.deleteById(clienteId);
    }

    // --- MÉTODOS DE VALIDAÇÃO (SEUS MÉTODOS EXISTENTES) ---

    // Este método irá validar os campos simples do DTO do Cliente
    private void validarClienteDTO(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            throw new CampoNuloException("Dados do cliente não podem ser nulos.");
        }
        if (clienteDTO.getNome() == null || clienteDTO.getNome().trim().isEmpty() || clienteDTO.getNome().length() > 150) {
            throw new CampoNuloException("Nome é obrigatório e deve ter no máximo 150 caracteres.");
        }
        // Chamando a validação de CPF que você já tinha
        // Nota: passar um ClienteDTO para verificarCpf requer adaptação ou criar uma sobrecarga.
        // Ou, como já validamos o CPF no início do cadastrarCliente, podemos confiar nessa validação inicial.
        // Se você quiser manter a lógica de verificação de CPF centralizada aqui:
        verificaCpfDoCliente(clienteDTO.getCpf());


        // Chamando a validação de Sexo que você já tinha
        // Adaptei para receber o Character diretamente, o que é mais limpo para DTOs.
        if (clienteDTO.getSexo() == null) {
             throw new CampoNuloException("Sexo é obrigatório.");
        }
        verificaSexo(clienteDTO.getSexo()); // Passando apenas o Character
    }

    // Adaptação do seu método verificarCpf para receber a String do CPF diretamente do DTO
    public Boolean verificaCpfDoCliente(String cpf) { // Renomeado para evitar conflito e ser mais claro
        if (cpf == null) {
            throw new CampoNuloException("Informe um CPF.");
        }

        String cpfLimpo = cpf.trim().replaceAll("[^0-9]", "");

        if (cpfLimpo.isEmpty()) {
            throw new DadosInvalidosException("CPF não pode ser vazio ou conter apenas caracteres inválidos.");
        }
        if (cpfLimpo.length() != 11) {
            throw new DadosInvalidosException("CPF deve ter 11 dígitos.");
        }
        if (cpfLimpo.matches("(\\d)\\1{10}")) {
            throw new DadosInvalidosException("CPF inválido: não pode ter todos os dígitos iguais.");
        }

        int[] digitos = new int[11];
        for (int i = 0; i < 11; i++) {
            digitos[i] = Character.getNumericValue(cpfLimpo.charAt(i));
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += digitos[i] * (10 - i);
        }
        int primeiroDigitoVerificador = 11 - (soma % 11);
        if (primeiroDigitoVerificador >= 10) {
            primeiroDigitoVerificador = 0;
        }

        if (digitos[9] != primeiroDigitoVerificador) {
            throw new DadosInvalidosException("CPF inválido: primeiro dígito verificador incorreto.");
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += digitos[i] * (11 - i);
        }
        int segundoDigitoVerificador = 11 - (soma % 11);
        if (segundoDigitoVerificador >= 10) {
            segundoDigitoVerificador = 0;
        }

        if (digitos[10] != segundoDigitoVerificador) {
            throw new DadosInvalidosException("CPF inválido: segundo dígito verificador incorreto.");
        }

        return true;
    }

    // Adaptação do seu método verificaSexo para receber o Character diretamente do DTO
    private Boolean verificaSexo(Character sexo) {
        if (sexo == null) {
            throw new CampoNuloException("Sexo é obrigatório."); // Já foi checado em validarClienteDTO
        }
        if (sexo != 'M' && sexo != 'F') {
            throw new DadosInvalidosException("Sexo inválido, o valor de sexo tem que ser M ou F.");
        }
        return true;
    }

    // Métodos de conversão (DTO para Entity)
    private Cliente fromClienteDTO(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setSexo(dto.getSexo());
        cliente.setDataNascimento(dto.getDataNascimento());
        return cliente;
    }

    private Endereco fromEnderecoDTO(EnderecoDTO dto) {
        Endereco endereco = new Endereco();
        endereco.setRua(dto.getRua());
        endereco.setNumero(dto.getNumero());
        endereco.setComplemento(dto.getComplemento());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setUf(dto.getUf());
        endereco.setCep(dto.getCep());
        return endereco;
    }

    private Telefone fromTelefoneDTO(TelefoneDTO dto) {
        Telefone telefone = new Telefone();
        telefone.setTipo(dto.getTipo());
        telefone.setNumero(dto.getNumero());
        telefone.setDdd(dto.getDdd());
        return telefone;
    }

    // Métodos de conversão (Entity para DTO)
    private ClienteDTO toClienteDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setClienteId(cliente.getClienteId());
        dto.setNome(cliente.getNome());
        dto.setCpf(cliente.getCpf());
        dto.setSexo(cliente.getSexo());
        dto.setDataNascimento(cliente.getDataNascimento());

        if (cliente.getEndereco() != null) {
            dto.setEndereco(toEnderecoDTO(cliente.getEndereco()));
        }

        if (cliente.getTelefones() != null && !cliente.getTelefones().isEmpty()) {
            dto.setTelefones(cliente.getTelefones().stream()
                .map(this::toTelefoneDTO)
                .collect(Collectors.toList()));
        }
        return dto;
    }

    private EnderecoDTO toEnderecoDTO(Endereco endereco) {
        EnderecoDTO dto = new EnderecoDTO();
        dto.setEnderecoId(endereco.getEnderecoId());
        dto.setRua(endereco.getRua());
        dto.setNumero(endereco.getNumero());
        dto.setComplemento(endereco.getComplemento());
        dto.setBairro(endereco.getBairro());
        dto.setCidade(endereco.getCidade());
        dto.setUf(endereco.getUf());
        dto.setCep(endereco.getCep());
        return dto;
    }

    private TelefoneDTO toTelefoneDTO(Telefone telefone) {
        TelefoneDTO dto = new TelefoneDTO();
        dto.setTelefoneId(telefone.getTelefoneId());
        dto.setTipo(telefone.getTipo());
        dto.setNumero(telefone.getNumero());
        dto.setDdd(telefone.getDdd());
        return dto;
    }

    // Métodos de serviço
    public List<ClienteDTO> listarTodosClientesDTO() {
        List<Cliente> clientes = clienteRepository.findAllWithRelationships();
        return clientes.stream()
            .map(this::toClienteDTO)
            .collect(Collectors.toList());
    }

    public ClienteDTO atualizarCliente(Long id, ClienteDTO clienteDTO) {
        Cliente clienteExistente = clienteRepository.findById(id)
            .orElseThrow(() -> new DadosNaoEncontradosException("Cliente não encontrado"));

        // Atualiza apenas os campos permitidos
        clienteExistente.setNome(clienteDTO.getNome());
        clienteExistente.setSexo(clienteDTO.getSexo());
        clienteExistente.setDataNascimento(clienteDTO.getDataNascimento());

        // Atualização do endereço se existir no DTO
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
            // Lógica para atualizar/remover/adicionar telefones
            // (Implementação mais complexa dependendo dos requisitos)
        }

        Cliente clienteAtualizado = clienteRepository.save(clienteExistente);
        return toClienteDTO(clienteAtualizado);
    }
}
