package br.ifg.urt.barbearia_api.service;

import br.ifg.urt.barbearia_api.dto.request.ServicoRequestDTO;
import br.ifg.urt.barbearia_api.dto.response.ServicoResponseDTO;
import br.ifg.urt.barbearia_api.mapper.ServicoMapper;
import br.ifg.urt.barbearia_api.model.Servico;
import br.ifg.urt.barbearia_api.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    public class ServicoService {

        private final ServicoRepository servicoRepository;

        public ServicoService(ServicoRepository servicoRepository) {
            this.servicoRepository = servicoRepository;
        }

        public ServicoResponseDTO criar(ServicoRequestDTO dto) {
            Servico servico = ServicoMapper.toEntity(dto);

            return ServicoMapper.toResponse(
                    servicoRepository.save(servico)
            );
        }

        public List<ServicoResponseDTO> listar() {
            return servicoRepository.findAll()
                    .stream()
                    .map(ServicoMapper::toResponse)
                    .toList();
        }

        public ServicoResponseDTO buscarPorId(Long id) {
            Servico servico = servicoRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("Serviço não encontrado"));

            return ServicoMapper.toResponse(servico);
        }

        public ServicoResponseDTO atualizar(Long id, ServicoRequestDTO dto) {

            Servico servico = servicoRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("Serviço não encontrado"));

            servico.setNome(dto.nome());
            servico.setDescricao(dto.descricao());
            servico.setValor(dto.valor());
            servico.setDuracaoMinutos(dto.duracaoMinutos());

            return ServicoMapper.toResponse(
                    servicoRepository.save(servico)
            );
        }

        public void deletar(Long id) {

            Servico servico = servicoRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("Serviço não encontrado"));

            servicoRepository.delete(servico);
        }
    }

