package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.model.Cliente;
import br.ifg.urt.barbearia_api.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {

        Optional<Cliente> cliente = clienteRepository.findById(id);

        if (cliente.isPresent()) {
            return cliente.get();
        }

        return null;
    }

    public Cliente atualizar(Long id, Cliente clienteAtualizado) {

        Cliente clienteExistente = buscarPorId(id);

        if (clienteExistente != null) {

            clienteExistente.setNome(clienteAtualizado.getNome());
            clienteExistente.setEmail(clienteAtualizado.getEmail());
            clienteExistente.setTelefone(clienteAtualizado.getTelefone());
            clienteExistente.setSenha(clienteAtualizado.getSenha());
            clienteExistente.setObservacoes(clienteAtualizado.getObservacoes());

            return clienteRepository.save(clienteExistente);
        }

        return null;
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
}