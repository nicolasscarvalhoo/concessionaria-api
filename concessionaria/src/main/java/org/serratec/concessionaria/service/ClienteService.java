package org.serratec.concessionaria.service;

import org.serratec.concessionaria.entity.Cliente;
import org.serratec.concessionaria.exception.ClienteNaoEncontradoException;
import org.serratec.concessionaria.exception.CpfJaCadastradoException;
import org.serratec.concessionaria.model.ClienteAtualizar;
import org.serratec.concessionaria.model.ClienteResponseDTO;
import org.serratec.concessionaria.model.ClienteCriar;
import org.serratec.concessionaria.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;
    private String appName;
    private String dbName;

    public ClienteService(ClienteRepository clienteRepository, @Value("${spring.application.name}") String appName,
                          @Value("${spring.datasource.url}") String dbName) {
        this.clienteRepository = clienteRepository;
        this.appName = appName;
        this.dbName = dbName;
    }

    public void inserir(ClienteCriar cliente) {

        cliente.setCpf(cliente.getCpf().replaceAll("\\D", ""));
        cliente.setTelefone(cliente.getTelefone().replaceAll("\\D", ""));
        if (this.clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new CpfJaCadastradoException("Já existe um cliente cadastrado com esse CPF.");
        }
        Cliente clienteInserir = new Cliente(cliente);
        this.clienteRepository.save(clienteInserir);
        System.out.println("Consegui salvar o dado no banco " + this.dbName);
    }

    public ClienteResponseDTO buscarPorId(UUID id) {
        Cliente cliente = this.clienteRepository
                .findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("O cliente não foi encontrado pelo id."));
        return new ClienteResponseDTO(cliente);

    }

    public List<ClienteResponseDTO> buscar(String cpf, String nome) {

        List<Cliente> clientes = new ArrayList<>();

        if(cpf != null && !cpf.isBlank()) {

            String cpfLimpo = cpf.replaceAll("//D", "");
            Optional<Cliente> clienteOpt = this.clienteRepository.findByCpf(cpfLimpo);
            clienteOpt.ifPresent(clientes::add);

        } else if(nome != null && !nome.isBlank()) {

            clientes = this.clienteRepository.findByNomeContainingIgnoreCase(nome);

        } else {

            clientes = this.clienteRepository.findAll();

        }
        if (clientes.isEmpty()) {

            throw new ClienteNaoEncontradoException("O cliente não foi encontrado dentro dos parâmetros.");

        }

        return clientes
                .stream()
                .map(cliente -> new ClienteResponseDTO(cliente))
                .toList();

    }

    public ClienteResponseDTO atualizar(UUID id, ClienteAtualizar clienteAtualizar) {
        Cliente clienteNoBanco = this.clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Não foi possível encontrar o cliente."));

        clienteNoBanco.setNome(clienteAtualizar.getNome());
        clienteNoBanco.setTelefone(clienteAtualizar.getTelefone().replaceAll("\\D", ""));
        clienteNoBanco.setEmail(clienteAtualizar.getEmail());

        this.clienteRepository.save(clienteNoBanco);
        return new ClienteResponseDTO(clienteNoBanco);
    }

    public void deletar (UUID id) {
        if (!this.clienteRepository.existsById(id)) {
            throw new ClienteNaoEncontradoException("Não foi possível deletar, pois o cliente não foi encontrado.");
        }
        this.clienteRepository.deleteById(id);
    }

}
