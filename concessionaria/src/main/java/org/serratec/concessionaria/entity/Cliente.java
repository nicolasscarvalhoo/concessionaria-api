package org.serratec.concessionaria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.serratec.concessionaria.model.ClienteCriar;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(length = 100, nullable = false)
    private String nome;

    @NotBlank
    @Size(min = 10, max = 15)
    @Column(length = 15, nullable = false)
    private String telefone;

    @NotBlank
    @Size(min = 11, max = 14)
    @Column(length = 14, unique = true, nullable= false)
    private String cpf;

    @NotBlank
    @Email // Impede que o usuário envie sem o '@'
    @Column(length=50, nullable = false)
    private String email;

    public Cliente(ClienteCriar cliente) {
        this.nome = cliente.getNome();
        this.telefone = cliente.getTelefone();
        this.cpf = cliente.getCpf();
        this.email = cliente.getEmail();

    }

}
