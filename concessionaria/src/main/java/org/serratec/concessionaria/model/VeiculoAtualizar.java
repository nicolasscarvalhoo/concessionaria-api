package org.serratec.concessionaria.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VeiculoAtualizar {

    @NotNull
    private BigDecimal valor;

    @NotNull
    private BigDecimal maximoDesconto;

    @NotNull
    private Boolean vendido;

    private BigDecimal valorVenda;
    private UUID clienteId;

}
