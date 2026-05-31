package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.ItemVendaRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ItemVendaResponseDTO;
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

    public ItemVendaService(
            ItemVendaRepository itemVendaRepository,
            ItemRepository itemRepository
    ) {
        this.itemVendaRepository = itemVendaRepository;
        this.itemRepository = itemRepository;
    }

    public ItemVendaResponseDTO criar(ItemVendaRequestDTO dto) {

        Item item = itemRepository.findById(dto.idItem())
                .orElseThrow(() ->
                        new RuntimeException("Item não encontrado"));

        ItemVenda itemVenda = new ItemVenda();

        itemVenda.setItem(item);
        itemVenda.setQuantidade(dto.quantidade());

        itemVenda.setValorUnitario(item.getValor());

        BigDecimal subtotal = item.getValor()
                .multiply(BigDecimal.valueOf(dto.quantidade()));

        itemVenda.setSubtotal(subtotal);

        ItemVenda salvo = itemVendaRepository.save(itemVenda);

        return new ItemVendaResponseDTO(
                salvo.getIdItemVenda(),
                item.getIdItem(),
                item.getNome(),
                salvo.getQuantidade(),
                salvo.getValorUnitario(),
                salvo.getSubtotal()
        );
    }

    public List<ItemVendaResponseDTO> listar() {

        return itemVendaRepository.findAll()
                .stream()
                .map(itemVenda -> new ItemVendaResponseDTO(
                        itemVenda.getIdItemVenda(),
                        itemVenda.getItem().getIdItem(),
                        itemVenda.getItem().getNome(),
                        itemVenda.getQuantidade(),
                        itemVenda.getValorUnitario(),
                        itemVenda.getSubtotal()
                ))
                .toList();
    }

    // ==========================================
    // MÉTODO NOVO 1: Buscar por ID
    // ==========================================
    public ItemVendaResponseDTO buscarPorId(Long id) {
        ItemVenda itemVenda = itemVendaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ItemVenda não encontrado"));

        return new ItemVendaResponseDTO(
                itemVenda.getIdItemVenda(),
                itemVenda.getItem().getIdItem(),
                itemVenda.getItem().getNome(),
                itemVenda.getQuantidade(),
                itemVenda.getValorUnitario(),
                itemVenda.getSubtotal()
        );
    }

    // ==========================================
    // MÉTODO NOVO 2: Atualizar
    // ==========================================
    public ItemVendaResponseDTO atualizar(Long id, ItemVendaRequestDTO dto) {
        // 1. Busca o ItemVenda existente
        ItemVenda itemVenda = itemVendaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ItemVenda não encontrado"));

        // 2. Busca o Item (Produto/Serviço) associado ao DTO
        Item item = itemRepository.findById(dto.idItem())
                .orElseThrow(() ->
                        new RuntimeException("Item não encontrado"));

        // 3. Atualiza os dados e recalcula as taxas
        itemVenda.setItem(item);
        itemVenda.setQuantidade(dto.quantidade());
        itemVenda.setValorUnitario(item.getValor());

        BigDecimal subtotal = item.getValor()
                .multiply(BigDecimal.valueOf(dto.quantidade()));
        itemVenda.setSubtotal(subtotal);

        // 4. Salva no banco de dados
        ItemVenda salvo = itemVendaRepository.save(itemVenda);

        // 5. Retorna o DTO atualizado
        return new ItemVendaResponseDTO(
                salvo.getIdItemVenda(),
                item.getIdItem(),
                item.getNome(),
                salvo.getQuantidade(),
                salvo.getValorUnitario(),
                salvo.getSubtotal()
        );
    }

    public void deletar(Long id) {

        ItemVenda itemVenda = itemVendaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ItemVenda não encontrado"));

        itemVendaRepository.delete(itemVenda);
    }
}