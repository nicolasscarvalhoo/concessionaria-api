package org.serratec.concessionaria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.serratec.concessionaria.model.VeiculoCriar;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne // Um Cliente pode comprar vários veículos
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotBlank
    @Column(length = 50, nullable = false)
    private String marca;

    @NotBlank
    @Column(length = 50, nullable = false)
    private String modelo;

    @NotNull
    @Column(nullable = false)
    private Integer ano;

    @NotNull
    @Column(nullable = false)
    private BigDecimal valor;

    @NotBlank
    @Column(length = 7, unique = true, nullable = false)
    private String placa;

    @NotNull
    @Column(nullable = false)
    private BigDecimal maximoDesconto;

    @NotNull
    @Column(nullable = false)
    private Boolean vendido;

    private BigDecimal valorVenda;

    public Veiculo(VeiculoCriar veiculo, Cliente cliente) {
        this.cliente = cliente;
        this.marca = veiculo.getMarca();
        this.modelo = veiculo.getModelo();
        this.ano = veiculo.getAno();
        this.valor = veiculo.getValor();
        this.placa = veiculo.getPlaca();
        this.maximoDesconto = veiculo.getMaximoDesconto();
        this.vendido = veiculo.getVendido();
        this.valorVenda = veiculo.getValorVenda();

    }
}
