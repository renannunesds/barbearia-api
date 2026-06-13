package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.ItemVendaRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ItemVendaResponseDTO;
import br.ifg.urt.barbearia_api.mapper.ItemVendaMapper;
import br.ifg.urt.barbearia_api.model.Item;
import br.ifg.urt.barbearia_api.model.ItemVenda;
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

        ItemVenda itemVenda = itemVendaMapper.toEntity(dto);
        itemVenda.setItem(item);
        itemVenda.setValorUnitario(item.getValor());

        // CORREÇÃO: Sem instanceof ou casting redundante.
        // Se o valor for nulo, usamos o ZERO, senão usamos o próprio valor.
        BigDecimal valorOriginal = (item.getValor() != null) ? item.getValor() : BigDecimal.ZERO;
        BigDecimal subtotal = valorOriginal.multiply(BigDecimal.valueOf(dto.quantidade()));

        // CORREÇÃO: Setamos o subtotal no objeto para a variável não ficar sem uso
        itemVenda.setSubtotal(subtotal);

        ItemVenda salvo = itemVendaRepository.save(itemVenda);
        return itemVendaMapper.toResponseDTO(salvo);
    }

    public List<ItemVendaResponseDTO> listar() {
        return itemVendaMapper.toResponseDTOList(itemVendaRepository.findAll());
    }

    public ItemVendaResponseDTO buscarPorId(Long id) {
        // ATUALIZADO: Usando findByIdOrThrow do ItemVendaRepository
        ItemVenda itemVenda = itemVendaRepository.findByIdOrThrow(id);
        return itemVendaMapper.toResponseDTO(itemVenda);
    }

    @Transactional
    public ItemVendaResponseDTO atualizar(Long id, ItemVendaRequestDTO dto) {
        ItemVenda itemVendaExistente = itemVendaRepository.findByIdOrThrow(id);
        Item item = itemRepository.findByIdOrThrow(dto.idItem());

        itemVendaExistente.setItem(item);
        itemVendaExistente.setQuantidade(dto.quantidade());
        itemVendaExistente.setValorUnitario(item.getValor());

        // CORREÇÃO: Limpeza do casting e instanceof redundante
        BigDecimal valorOriginal = (item.getValor() != null) ? item.getValor() : BigDecimal.ZERO;
        BigDecimal subtotal = valorOriginal.multiply(BigDecimal.valueOf(dto.quantidade()));

        // CORREÇÃO: Setamos o subtotal no objeto existente
        itemVendaExistente.setSubtotal(subtotal);

        ItemVenda atualizado = itemVendaRepository.save(itemVendaExistente);
        return itemVendaMapper.toResponseDTO(atualizado);
    }

    @Transactional
    public void deletar(Long id) {
        // ATUALIZADO: Usando findByIdOrThrow do ItemVendaRepository
        ItemVenda itemVenda = itemVendaRepository.findByIdOrThrow(id);
        itemVendaRepository.delete(itemVenda);
    }
}