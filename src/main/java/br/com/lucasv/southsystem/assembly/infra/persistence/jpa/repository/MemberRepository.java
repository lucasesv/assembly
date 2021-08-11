package br.com.lucasv.southsystem.assembly.infra.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.entity.MemberJpa;

/**
 * JPA Repository for manipulating {@link Member} stored data.
 * 
 * @author Lucas Vasconcelos
 *
 */
public interface MemberRepository extends JpaRepository<MemberJpa, Integer>{

}
