package org.serratec.concessionaria.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.AllArgsConstructor;


import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VeiculoCriar {

    private UUID clienteId;

    @NotBlank
    private String marca;

    @NotBlank
    private String modelo;

    @NotNull
    private Integer ano;

    @NotNull
    private BigDecimal valor;

    @NotBlank
    @Size(min = 7, max = 7)
    private String placa;

    @NotNull
    private BigDecimal maximoDesconto;

    @NotNull
    private Boolean vendido;

    private BigDecimal valorVenda;

}
