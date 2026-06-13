package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.ProdutoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ProdutoResponseDTO;
import br.ifg.urt.barbearia_api.mapper.ProdutoMapper;
import br.ifg.urt.barbearia_api.model.Produto;
import br.ifg.urt.barbearia_api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importe correto
import java.util.List;

@Service
@Transactional(readOnly = true) // Transforma toda a classe em leitura por padrão
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    @Transactional // Sobrescreve o topo porque este método ESCEVE no banco
    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {
        Produto produto = produtoMapper.toEntity(dto);
        return produtoMapper.toResponseDTO(produtoRepository.save(produto));
    }

    // Não precisa de anotação aqui, herda o readOnly = true do topo
    public List<ProdutoResponseDTO> listar() {
        return produtoMapper.toResponseDTOList(produtoRepository.findAll());
    }

    // Herda o readOnly = true do topo
    public ProdutoResponseDTO buscarPorId(Long id) {
        // Atualizado para usar o método padrão que criamos no seu Repository!
        Produto produto = produtoRepository.findByIdOrThrow(id);
        return produtoMapper.toResponseDTO(produto);
    }

    @Transactional // Sobrescreve o topo porque este método ALTERA o banco
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Produto produto = produtoRepository.findByIdOrThrow(id);

        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setQuantidadeEstoque(dto.quantidadeEstoque());

        return produtoMapper.toResponseDTO(produtoRepository.save(produto));
    }

    @Transactional // Sobrescreve o topo porque este método DELETA do banco
    public void deletar(Long id) {
        Produto produto = produtoRepository.findByIdOrThrow(id);
        produtoRepository.delete(produto);
    }
}