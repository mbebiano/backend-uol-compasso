package com.compasso.uol.repositories;

import com.compasso.uol.enums.EstadoEnum;
import com.compasso.uol.models.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>, CrudRepository<Cidade, Long> {

    List<Cidade> findAllByNomeIgnoreCase(String nome);

    Cidade findByNomeIgnoreCase(String nome);

    List<Cidade> findAllByEstado(EstadoEnum estadoEnum);
}
