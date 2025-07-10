package br.com.loja.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.loja.app.entity.Telefone;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long>{

}
