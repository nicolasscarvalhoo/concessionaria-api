package org.serratec.concessionaria.controller;

import jakarta.validation.Valid;
import org.serratec.concessionaria.model.VeiculoAtualizar;
import org.serratec.concessionaria.model.VeiculoResponseDTO;
import org.serratec.concessionaria.model.VeiculoCriar;
import org.serratec.concessionaria.service.VeiculoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<VeiculoResponseDTO> buscar(@PathVariable UUID id) {

        VeiculoResponseDTO veiculo = this.veiculoService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(veiculo);

    }

    @GetMapping("/pesquisa")
    public ResponseEntity<List<VeiculoResponseDTO>> buscar(@RequestParam(required = false) String placa, @RequestParam(required = false) String marca,
                                                           @RequestParam(required = false) String modelo) {

        List<VeiculoResponseDTO> veiculos = this.veiculoService.buscar(placa, marca, modelo);
        return ResponseEntity.status(HttpStatus.OK).body(veiculos);

    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> atualizar(@PathVariable UUID id, @Valid @RequestBody VeiculoAtualizar veiculoAtualizar) {

        return ResponseEntity.status(HttpStatus.OK).body(this.veiculoService.atualizar(id, veiculoAtualizar));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deletar(@PathVariable UUID id) {

        this.veiculoService.deletar(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<Page<VeiculoResponseDTO>> listarVeiculos(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)Pageable pageable) {

        Page<VeiculoResponseDTO> veiculos = veiculoService.listarTodos(pageable);
        return ResponseEntity.ok(veiculos);

    }

}
