package org.serratec.concessionaria.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.AllArgsConstructor;

import org.serratec.concessionaria.entity.Veiculo;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) //Faz com que caso o carro não foi vendido, os campos valorVenda e cliente nem apareçam para o usuário.
public class VeiculoBuscaId {

    private UUID id;
    private String marca;
    private String modelo;
    private Integer ano;
    private BigDecimal valor;
    private String placa;
    private BigDecimal maximoDesconto;
    private Boolean vendido;
    private BigDecimal valorVenda;
    private String nomeCliente;

    public VeiculoBuscaId(Veiculo veiculo) {

        this.id = veiculo.getId();
        this.marca = veiculo.getMarca();
        this.modelo = veiculo.getModelo();
        this.ano = veiculo.getAno();
        this.valor = veiculo.getValor();
        this.placa = veiculo.getPlaca();
        this.maximoDesconto = veiculo.getMaximoDesconto();
        this.vendido = veiculo.getVendido();

        if (Boolean.TRUE.equals(this.vendido)) {
            this.valorVenda = veiculo.getValorVenda();
            this.nomeCliente = veiculo.getCliente().getNome();
        }

    }

}
