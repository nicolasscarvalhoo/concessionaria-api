package org.serratec.concessionaria.controller;

import jakarta.validation.Valid;
import org.serratec.concessionaria.model.VeiculoAtualizar;
import org.serratec.concessionaria.model.VeiculoBuscaId;
import org.serratec.concessionaria.model.VeiculoCriar;
import org.serratec.concessionaria.service.VeiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {

        this.veiculoService = veiculoService;

    }

    @PostMapping
    public ResponseEntity<Void> inserir(@Valid @RequestBody VeiculoCriar veiculo) {

        this.veiculoService.inserir(veiculo);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoBuscaId> buscar(@PathVariable UUID id) {

        VeiculoBuscaId veiculo = this.veiculoService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(veiculo);

    }

    @GetMapping()
    public ResponseEntity<List<VeiculoBuscaId>> buscar(@RequestParam(required = false) String placa, @RequestParam(required = false) String marca,
                                                       @RequestParam(required = false) String modelo) {

        List<VeiculoBuscaId> veiculos = this.veiculoService.buscar(placa, marca, modelo);
        return ResponseEntity.status(HttpStatus.OK).body(veiculos);

    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoBuscaId> atualizar(@PathVariable UUID id, @Valid @RequestBody VeiculoAtualizar veiculoAtualizar) {

        return ResponseEntity.status(HttpStatus.OK).body(this.veiculoService.atualizar(id, veiculoAtualizar));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deletar(@PathVariable UUID id) {

        this.veiculoService.deletar(id);
        return ResponseEntity.noContent().build();

    }
}
