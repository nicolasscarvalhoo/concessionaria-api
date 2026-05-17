package org.serratec.concessionaria.repository;

import org.serratec.concessionaria.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    boolean existsByCpf(String cpf);

    Optional<Cliente> findByCpf(String cpf);

    List<Cliente> findByNomeContainingIgnoreCase(String nome);

}

