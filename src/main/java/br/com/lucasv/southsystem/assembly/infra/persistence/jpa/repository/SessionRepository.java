package br.com.lucasv.southsystem.assembly.infra.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.entity.SessionJpa;

/**
 * JPA Repository for manipulating {@link Session} stored data.
 * 
 * @author Lucas Vasconcelos
 *
 */
public interface SessionRepository extends JpaRepository<SessionJpa, Integer>{

}
