package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.ItemVendaRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ItemVendaResponseDTO;
import br.ifg.urt.barbearia_api.mapper.ItemVendaMapper;
import br.ifg.urt.barbearia_api.model.Item;
import br.ifg.urt.barbearia_api.model.ItemVenda;
import br.ifg.urt.barbearia_api.repository.ItemRepository;
import br.ifg.urt.barbearia_api.repository.ItemVendaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ItemVendaService {

    private final ItemVendaRepository itemVendaRepository;
    private final ItemRepository itemRepository;
    private final ItemVendaMapper itemVendaMapper;

    public ItemVendaService(
            ItemVendaRepository itemVendaRepository,
            ItemRepository itemRepository,
            ItemVendaMapper itemVendaMapper
    ) {
        this.itemVendaRepository = itemVendaRepository;
        this.itemRepository = itemRepository;
        this.itemVendaMapper = itemVendaMapper;
    }

    public ItemVendaResponseDTO criar(ItemVendaRequestDTO dto) {
        Item item = itemRepository.findById(dto.idItem())
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        ItemVenda itemVenda = itemVendaMapper.toEntity(dto);
        itemVenda.setItem(item);
        itemVenda.setValorUnitario(item.getValor());

        BigDecimal valorOriginal = (item.getValor() instanceof BigDecimal)
                ? (BigDecimal) item.getValor()
                : BigDecimal.ZERO;

        BigDecimal subtotal = valorOriginal.multiply(BigDecimal.valueOf(dto.quantidade()));
        // itemVenda.setSubtotal(subtotal); // Atribua se o setSubtotal existir na sua entidade

        ItemVenda salvo = itemVendaRepository.save(itemVenda);
        return itemVendaMapper.toResponseDTO(salvo);
    }

    public List<ItemVendaResponseDTO> listar() {
        return itemVendaMapper.toResponseDTOList(itemVendaRepository.findAll());
    }


    public ItemVendaResponseDTO buscarPorId(Long id) {
        ItemVenda itemVenda = itemVendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ItemVenda não encontrado"));
        return itemVendaMapper.toResponseDTO(itemVenda);
    }


    public ItemVendaResponseDTO atualizar(Long id, ItemVendaRequestDTO dto) {
        ItemVenda itemVendaExistente = itemVendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ItemVenda não encontrado"));

        Item item = itemRepository.findById(dto.idItem())
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        // Atualiza os dados do item de venda com as novas informações do DTO
        itemVendaExistente.setItem(item);
        itemVendaExistente.setQuantidade(dto.quantidade());
        itemVendaExistente.setValorUnitario(item.getValor());

        BigDecimal valorOriginal = (item.getValor() instanceof BigDecimal)
                ? (BigDecimal) item.getValor()
                : BigDecimal.ZERO;

        BigDecimal subtotal = valorOriginal.multiply(BigDecimal.valueOf(dto.quantidade()));
        // itemVendaExistente.setSubtotal(subtotal); // Atribua se o setSubtotal existir na sua entidade

        ItemVenda atualizado = itemVendaRepository.save(itemVendaExistente);
        return itemVendaMapper.toResponseDTO(atualizado);
    }

    public void deletar(Long id) {
        ItemVenda itemVenda = itemVendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ItemVenda não encontrado"));

        itemVendaRepository.delete(itemVenda);
    }
}