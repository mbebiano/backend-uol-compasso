package com.compasso.uol.repositories;

import com.compasso.uol.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, CrudRepository<Cliente, Long> {

    List<Cliente> findAllByNomeCompletoIgnoreCase(String nome);
}
