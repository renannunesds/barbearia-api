package br.ifg.urt.barbearia_api.mapper;


import br.ifg.urt.barbearia_api.dto.request.ProdutoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ProdutoResponseDTO;
import br.ifg.urt.barbearia_api.model.Produto;

public class ProdutoMapper {

    public static Produto toEntity(ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setValor(dto.valor());
        produto.setQuantidadeEstoque(dto.quantidadeEstoque());
        return produto;
    }

    public static ProdutoResponseDTO toResponse(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getIdItem(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getValor(),
                produto.getQuantidadeEstoque()
        );
    }
}
