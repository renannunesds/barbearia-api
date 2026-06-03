package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.ProdutoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ProdutoResponseDTO;
import br.ifg.urt.barbearia_api.mapper.ProdutoMapper;
import br.ifg.urt.barbearia_api.model.Produto;
import br.ifg.urt.barbearia_api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper; // Injetando o novo Mapper do MapStruct

    // O construtor agora recebe o produtoMapper
    public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {
        Produto produto = produtoMapper.toEntity(dto);
        return produtoMapper.toResponseDTO(produtoRepository.save(produto));
    }

    public List<ProdutoResponseDTO> listar() {
        return produtoMapper.toResponseDTOList(produtoRepository.findAll());
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        return produtoMapper.toResponseDTO(produto);
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());

        // Se seu modelo usa Value Object para valor, a atribuição é tratada no Mapper ao criar,
        // ou você pode atualizar aqui conforme seu modelo de domínio.
        // produto.setValor(dto.valor());

        return produtoMapper.toResponseDTO(produtoRepository.save(produto));
    }

    public void deletar(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produtoRepository.delete(produto);
    }
}