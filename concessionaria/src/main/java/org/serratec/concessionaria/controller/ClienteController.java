package org.serratec.concessionaria.controller;


import jakarta.validation.Valid;
import org.serratec.concessionaria.model.ClienteAtualizar;
import org.serratec.concessionaria.model.ClienteResponseDTO;
import org.serratec.concessionaria.model.ClienteCriar;
import org.serratec.concessionaria.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {

        this.clienteService = clienteService;

    }

    @PostMapping
    public ResponseEntity<Void> inserir(@Valid @RequestBody ClienteCriar cliente) {

        this.clienteService.inserir(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscar(@PathVariable UUID id) {

        ClienteResponseDTO cliente = this.clienteService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);

    }

    @GetMapping()
    public ResponseEntity<List<ClienteResponseDTO>> buscar(@RequestParam(required = false) String cpf, @RequestParam(required = false) String nome) {

        List<ClienteResponseDTO> clientes = this.clienteService.buscar(cpf, nome);
        return ResponseEntity.status(HttpStatus.OK).body(clientes);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable UUID id, @Valid @RequestBody ClienteAtualizar clienteAtualizar) {

        return ResponseEntity.status(HttpStatus.OK).body(this.clienteService.atualizar(id, clienteAtualizar));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deletar(@PathVariable UUID id) {

        this.clienteService.deletar(id);
        return ResponseEntity.noContent().build();

    }
}
