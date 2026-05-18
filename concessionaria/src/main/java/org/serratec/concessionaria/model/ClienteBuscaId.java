package org.serratec.concessionaria.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.serratec.concessionaria.entity.Cliente;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteBuscaId {

    private UUID id;
    private String nome;
    private String telefone;
    private String cpf;
    private String email;

    public ClienteBuscaId(Cliente cliente) {

        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.telefone = formatTelefone(cliente.getTelefone());
        this.cpf = formatCPF(cliente.getCpf());
        this.email = cliente.getEmail();

    }

    private String formatCPF(String cpf) {
        if (cpf != null && cpf.length() == 11) {
            String cpfFormated = "";
            cpfFormated += cpf.substring(0, 3) + ".";
            cpfFormated += cpf.substring(3, 6) + ".";
            cpfFormated += cpf.substring(6, 9) + "-";
            cpfFormated += cpf.substring(9);
            return cpfFormated;
        }
        return cpf;
    }

    private String formatTelefone(String telefone) {
        if (telefone != null && telefone.length() == 11) {
            return "(" + telefone.substring(0, 2) + ") "
                    + telefone.substring(2, 7) + "-"
                    + telefone.substring(7);
        }
        return telefone;
    }


}
