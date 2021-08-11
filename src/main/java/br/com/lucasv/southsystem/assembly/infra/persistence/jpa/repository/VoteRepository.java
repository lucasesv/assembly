package br.com.lucasv.southsystem.assembly.infra.persistence.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.entity.VoteJpa;

/**
 * JPA Repository for manipulating {@link Vote} stored data.
 * 
 * @author Lucas Vasconcelos
 *
 */
public interface VoteRepository extends JpaRepository<VoteJpa, Integer>{

  List<VoteJpa> findBySessionId(Integer sessionId);
}
