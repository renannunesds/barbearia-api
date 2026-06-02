package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.ClienteRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ClienteResponseDTO;
import br.ifg.urt.barbearia_api.mapper.ClienteMapper;
import br.ifg.urt.barbearia_api.model.Cliente;
import br.ifg.urt.barbearia_api.model.vo.EmailVO;
import br.ifg.urt.barbearia_api.model.vo.TelefoneVO;
import br.ifg.urt.barbearia_api.model.vo.SenhaVO;
import br.ifg.urt.barbearia_api.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    public ClienteService(ClienteRepository repository, ClienteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ClienteResponseDTO> findAll() {
        List<Cliente> clientes = repository.findAll();
        return mapper.toResponseDTOList(clientes);
    }

    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return mapper.toResponseDTO(cliente);
    }

    @Transactional
    public ClienteResponseDTO create(ClienteRequestDTO dto) {
        Cliente cliente = mapper.toEntity(dto);
        Cliente clienteSalvo = repository.save(cliente);
        return mapper.toResponseDTO(clienteSalvo);
    }

    @Transactional
    public ClienteResponseDTO update(Long id, ClienteRequestDTO dto) {
        Cliente clienteExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        clienteExistente.setNome(dto.nome());
        clienteExistente.setEmail(new EmailVO(dto.email()));
        clienteExistente.setTelefone(new TelefoneVO(dto.telefone()));
        clienteExistente.setSenha(new SenhaVO(dto.senha()));
        clienteExistente.setObservacoes(dto.observacoes());

        Cliente atualizado = repository.save(clienteExistente);
        return mapper.toResponseDTO(atualizado);
    }

    @Transactional
    public void delete(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        repository.delete(cliente);
    }
}