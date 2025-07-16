package br.com.loja.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.loja.app.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    Optional<Cliente> findByCpf(String cpf);


    @Query("SELECT DISTINCT c FROM Cliente c LEFT JOIN FETCH c.endereco LEFT JOIN FETCH c.telefones")
        List<Cliente> findAllWithRelationships();
}
