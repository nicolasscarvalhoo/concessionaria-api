package org.serratec.concessionaria.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteAtualizar {

    @NotBlank
    private String nome;

    @NotBlank
    @Size(min = 10, max = 15)
    private String telefone;

    @NotBlank
    @Email
    private String email;
}
