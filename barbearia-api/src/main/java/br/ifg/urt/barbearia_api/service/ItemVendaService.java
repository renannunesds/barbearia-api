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
    private final ItemVendaMapper itemVendaMapper; // Injetando o novo Mapper

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

        // Se item.getValor() retornar um Value Object no seu projeto real,
        // mude para buscar a propriedade interna (ex: item.getValor().getValor())
        itemVenda.setValorUnitario(item.getValor());

        // Tratamento da regra de negócio de subtotal mantida no Service:
        BigDecimal valorOriginal = (item.getValor() instanceof BigDecimal)
                ? (BigDecimal) item.getValor()
                : BigDecimal.ZERO; // Ajuste dinâmico baseado no tipo do seu modelo

        BigDecimal subtotal = valorOriginal.multiply(BigDecimal.valueOf(dto.quantidade()));
        // itemVenda.setSubtotal(subtotal); // Atribui conforme o tipo do objeto no seu projeto

        ItemVenda salvo = itemVendaRepository.save(itemVenda);
        return itemVendaMapper.toResponseDTO(salvo);
    }

    public List<ItemVendaResponseDTO> listar() {
        return itemVendaMapper.toResponseDTOList(itemVendaRepository.findAll());
    }

    public void deletar(Long id) {
        ItemVenda itemVenda = itemVendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ItemVenda não encontrado"));

        itemVendaRepository.delete(itemVenda);
    }
}