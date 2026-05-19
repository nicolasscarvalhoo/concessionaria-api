package org.serratec.concessionaria.service;


import org.serratec.concessionaria.entity.Cliente;
import org.serratec.concessionaria.entity.Veiculo;
import org.serratec.concessionaria.exception.*;
import org.serratec.concessionaria.model.VeiculoAtualizar;
import org.serratec.concessionaria.model.VeiculoBuscaId;
import org.serratec.concessionaria.model.VeiculoCriar;
import org.serratec.concessionaria.repository.ClienteRepository;
import org.serratec.concessionaria.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VeiculoService {

    private ClienteRepository clienteRepository;
    private VeiculoRepository veiculoRepository;
    private String appName;
    private String dbName;

    public VeiculoService(VeiculoRepository veiculoRepository, ClienteRepository clienteRepository,
                          @Value("${spring.application.name}") String appName,
                          @Value("${spring.datasource.url}") String dbName) {

        this.clienteRepository = clienteRepository;
        this.veiculoRepository = veiculoRepository;
        this.appName = appName;
        this.dbName = dbName;

    }

    public void inserir(VeiculoCriar veiculo) {

        veiculo.setPlaca(veiculo.getPlaca().replaceAll("-", "").trim().toUpperCase());

        if (veiculoRepository.existsByPlaca(veiculo.getPlaca())) {
            throw new PlacaJaCadastradaException("Já existe um veículo cadastrado com esta placa.");
        }

        Cliente clienteDoCarro = null;

        if (Boolean.FALSE.equals(veiculo.getVendido())) {
            if (veiculo.getClienteId() != null) {
                throw new CadastrarClienteSemTerVendidoException("Não é possível cadastrar o cliente sem ter vendido o carro.");
            } else if (veiculo.getValorVenda() != null) {
                throw new CadastrarValorSemTerVendidoException("Não é possível cadastrar o valor da venda sem ter vendido o carro.");
            }
        } else {
            if (veiculo.getClienteId() == null) {
                throw new ClienteObrigatorioException("É obrigatório cadastrar um cliente para a venda.");
            } else if (veiculo.getValorVenda() == null) {
                throw new ValorVendaObrigatorioException("É obrigatório cadastrar o valor da venda. ");
            }

            clienteDoCarro = this.clienteRepository.findById(veiculo.getClienteId())
                    .orElseThrow(() -> new ClienteNaoEncontradoException("O cliente não foi encontrado."));
        }

            Veiculo veiculoInserir = new Veiculo(veiculo, clienteDoCarro);
            this.veiculoRepository.save(veiculoInserir);

    }

    public VeiculoBuscaId buscarPorId(UUID id) {
        Veiculo veiculo = this.veiculoRepository
                .findById(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException("O veículo não foi encontrado pelo id."));

        return new VeiculoBuscaId(veiculo);
    }

    public List<VeiculoBuscaId> buscar(String placa, String marca, String modelo) {

        List<Veiculo> veiculos = new ArrayList<>();

        if (placa != null && !placa.isBlank()) {

            String placaLimpa = placa.replace("-", "").trim().toUpperCase();
            Optional<Veiculo> veiculoOpt = this.veiculoRepository.findByPlaca(placa);
            veiculoOpt.ifPresent(veiculos::add);

        } else if (marca != null && !marca.isBlank()) {

            veiculos = this.veiculoRepository.findByMarcaContainingIgnoreCase(marca);

        } else if (modelo != null && !modelo.isBlank()) {

            veiculos = this.veiculoRepository.findByModeloContainingIgnoreCase(modelo);

        } else {
            veiculos = this.veiculoRepository.findAll();
        }

        if (veiculos.isEmpty()) {

            throw new VeiculoNaoEncontradoException("O veículo não foi encontrado dentro dos parâmetros.");
        }

        return veiculos
                .stream()
                .map(veiculo -> new VeiculoBuscaId(veiculo))
                .toList();
    }

    public VeiculoBuscaId atualizar(UUID id, VeiculoAtualizar veiculoAtualizar) {
        Veiculo veiculoNoBanco = this.veiculoRepository.findById(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException("O veículo não foi encontrado."));

        if (Boolean.FALSE.equals(veiculoAtualizar.getVendido())) {
            veiculoNoBanco.setVendido(false);
            veiculoNoBanco.setValorVenda(null);
            veiculoNoBanco.setCliente(null);
        } else {
            if (veiculoAtualizar.getClienteId() == null) {
                throw new ClienteObrigatorioException("Para marcar o veiculo como vendido é preciso informar o cliente.");
            } else if (veiculoAtualizar.getValorVenda() == null) {
                throw new ValorVendaObrigatorioException("Para marcar o veiculo como vendido é preciso informar o valor da venda.");
            }

            java.math.BigDecimal valorMinimoPermitido = veiculoAtualizar.getValor().subtract(veiculoAtualizar.getMaximoDesconto());

            if(veiculoAtualizar.getValorVenda().compareTo(valorMinimoPermitido) < 0) {
                throw new DescontoInvalidoException("O valor da venda ultrapassa o desconto máximo permitido para este veículo.");
            }

            Cliente cliente = this.clienteRepository.findById(veiculoAtualizar.getClienteId())
                    .orElseThrow(() -> new ClienteNaoEncontradoException("O cliente não foi encontrado pelo id."));

            veiculoNoBanco.setVendido(true);
            veiculoNoBanco.setValorVenda(veiculoAtualizar.getValorVenda());
            veiculoNoBanco.setCliente(cliente);

        }

        veiculoNoBanco.setValor(veiculoAtualizar.getValor());
        veiculoNoBanco.setMaximoDesconto(veiculoAtualizar.getMaximoDesconto());

        this.veiculoRepository.save(veiculoNoBanco);
        return new VeiculoBuscaId(veiculoNoBanco);

    }

    public void deletar (UUID id) {

        if (!this.veiculoRepository.existsById(id)) {

            throw new VeiculoNaoEncontradoException("O veículo não foi encontrado pelo id.");

        }

        this.veiculoRepository.deleteById(id);

    }

}
