package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.model.Barbeiro;
import br.ifg.urt.barbearia_api.repository.BarbeiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BarbeiroService {

    @Autowired
    private BarbeiroRepository barbeiroRepository;

    public Barbeiro salvar(Barbeiro barbeiro) {
        return barbeiroRepository.save(barbeiro);
    }

    public List<Barbeiro> listarTodos() {
        return barbeiroRepository.findAll();
    }

    public Barbeiro buscarPorId(Long id) {

        Optional<Barbeiro> barbeiro = barbeiroRepository.findById(id);

        if (barbeiro.isPresent()) {
            return barbeiro.get();
        }

        return null;
    }

    public Barbeiro atualizar(Long id, Barbeiro barbeiroAtualizado) {

        Barbeiro barbeiroExistente = buscarPorId(id);

        if (barbeiroExistente != null) {

            barbeiroExistente.setNome(barbeiroAtualizado.getNome());
            barbeiroExistente.setEmail(barbeiroAtualizado.getEmail());
            barbeiroExistente.setTelefone(barbeiroAtualizado.getTelefone());
            barbeiroExistente.setSenha(barbeiroAtualizado.getSenha());
            barbeiroExistente.setEspecialidade(barbeiroAtualizado.getEspecialidade());
            barbeiroExistente.setAtivo(barbeiroAtualizado.getAtivo());

            return barbeiroRepository.save(barbeiroExistente);
        }

        return null;
    }

    public void ativarBarbeiro(Long id) {

        Barbeiro barbeiro = buscarPorId(id);

        if (barbeiro != null) {
            barbeiro.setAtivo(true);
            barbeiroRepository.save(barbeiro);
        }
    }

    public void desativarBarbeiro(Long id) {

        Barbeiro barbeiro = buscarPorId(id);

        if (barbeiro != null) {
            barbeiro.setAtivo(false);
            barbeiroRepository.save(barbeiro);
        }
    }

    public void deletar(Long id) {
        barbeiroRepository.deleteById(id);
    }
}