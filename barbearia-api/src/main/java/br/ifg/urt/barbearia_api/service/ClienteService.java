package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.ClienteRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ClienteResponseDTO;
import br.ifg.urt.barbearia_api.mapper.ClienteMapper;
import br.ifg.urt.barbearia_api.model.Cliente;
import br.ifg.urt.barbearia_api.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    public ClienteService(
            ClienteRepository repository,
            ClienteMapper mapper) {

        this.repository = repository;
        this.mapper = mapper;
    }

    // Buscar todos
    public List<ClienteResponseDTO> findAll() {

        List<Cliente> clientes = repository.findAll();

        return mapper.toResponseDTOList(clientes);
    }

    // Buscar por ID
    public ClienteResponseDTO findById(Long id) {

        Cliente cliente = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cliente não encontrado"));

        return mapper.toResponseDTO(cliente);
    }

    // Criar cliente
    public ClienteResponseDTO create(ClienteRequestDTO dto) {

        Cliente cliente = mapper.toEntity(dto);

        Cliente clienteSalvo = repository.save(cliente);

        return mapper.toResponseDTO(clienteSalvo);
    }

    // Atualizar cliente
    public ClienteResponseDTO update(Long id, ClienteRequestDTO dto) {

        Cliente clienteExistente = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cliente não encontrado"));

        clienteExistente.setNome(dto.nome());
        clienteExistente.setEmail(dto.email());
        clienteExistente.setTelefone(dto.telefone());
        clienteExistente.setSenha(dto.senha());
        clienteExistente.setObservacoes(dto.observacoes());

        Cliente atualizado = repository.save(clienteExistente);

        return mapper.toResponseDTO(atualizado);
    }

    // Deletar cliente
    public void delete(Long id) {

        Cliente cliente = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cliente não encontrado"));

        repository.delete(cliente);
    }
}