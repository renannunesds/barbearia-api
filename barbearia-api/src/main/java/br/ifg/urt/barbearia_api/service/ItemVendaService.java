package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.ItemVendaRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ItemVendaResponseDTO;
import br.ifg.urt.barbearia_api.mapper.ItemVendaMapper;
import br.ifg.urt.barbearia_api.model.Item;
import br.ifg.urt.barbearia_api.model.ItemVenda;
import br.ifg.urt.barbearia_api.model.Produto; // Certifique-se deste import
import br.ifg.urt.barbearia_api.repository.ItemRepository;
import br.ifg.urt.barbearia_api.repository.ItemVendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ItemVendaService {

    private final ItemVendaRepository itemVendaRepository;
    private final ItemRepository itemRepository;
    private final ItemVendaMapper itemVendaMapper;

    public ItemVendaService(ItemVendaRepository itemVendaRepository,
                            ItemRepository itemRepository,
                            ItemVendaMapper itemVendaMapper) {
        this.itemVendaRepository = itemVendaRepository;
        this.itemRepository = itemRepository;
        this.itemVendaMapper = itemVendaMapper;
    }

    @Transactional
    public ItemVendaResponseDTO criar(ItemVendaRequestDTO dto) {
        Item item = itemRepository.findByIdOrThrow(dto.idItem());

        if (item instanceof Produto produto) {
            if (produto.getQuantidadeEstoque() < dto.quantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
            }
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - dto.quantidade());
        }

        ItemVenda itemVenda = itemVendaMapper.toEntity(dto);
        itemVenda.setItem(item);
        itemVenda.setValorUnitario(item.getValor());
        itemVenda.setSubtotal(item.getValor().multiply(BigDecimal.valueOf(dto.quantidade())));

        return itemVendaMapper.toResponseDTO(itemVendaRepository.save(itemVenda));
    }

    public List<ItemVendaResponseDTO> listar() {
        return itemVendaMapper.toResponseDTOList(itemVendaRepository.findAll());
    }

    public ItemVendaResponseDTO buscarPorId(Long id) {
        return itemVendaMapper.toResponseDTO(itemVendaRepository.findByIdOrThrow(id));
    }

    @Transactional
    public ItemVendaResponseDTO atualizar(Long id, ItemVendaRequestDTO dto) {
        ItemVenda itemVendaExistente = itemVendaRepository.findByIdOrThrow(id);

        devolverEstoque(itemVendaExistente);

        Item novoItem = itemRepository.findByIdOrThrow(dto.idItem());

        if (novoItem instanceof Produto produto) {
            if (produto.getQuantidadeEstoque() < dto.quantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
            }
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - dto.quantidade());
        }

        itemVendaExistente.setItem(novoItem);
        itemVendaExistente.setQuantidade(dto.quantidade());
        itemVendaExistente.setValorUnitario(novoItem.getValor());
        itemVendaExistente.setSubtotal(novoItem.getValor().multiply(BigDecimal.valueOf(dto.quantidade())));

        return itemVendaMapper.toResponseDTO(itemVendaRepository.save(itemVendaExistente));
    }

    @Transactional
    public void deletar(Long id) {
        ItemVenda itemVenda = itemVendaRepository.findByIdOrThrow(id);
        devolverEstoque(itemVenda);
        itemVendaRepository.delete(itemVenda);
    }

    private void devolverEstoque(ItemVenda itemVenda) {
        if (itemVenda.getItem() instanceof Produto produto) {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + itemVenda.getQuantidade());
        }
    }
}