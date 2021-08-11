package br.com.lucasv.southsystem.assembly.infra.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.entity.SubjectJpa;

/**
 * JPA Repository for manipulating {@link Subject} stored data.
 * 
 * @author Lucas Vasconcelos
 *
 */
public interface SubjectRepository extends JpaRepository<SubjectJpa, Integer> {

}
