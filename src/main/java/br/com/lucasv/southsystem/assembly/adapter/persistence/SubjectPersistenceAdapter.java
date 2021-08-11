package br.com.lucasv.southsystem.assembly.adapter.persistence;

import br.com.lucasv.southsystem.assembly.core.entity.Subject;

/**
 * <p>A Persistence Adapter to manipulate stored data about Subject.
 * 
 * <p>It is an Persistence Abastraction Layer so that the core
 * use cases can be decoupled of the persistence infrastructure.
 * 
 * @author Lucas Vasconcelos
 *
 */
public interface SubjectPersistenceAdapter {

  int saveSubject(Subject subject);

  Subject getSubject(int id);

}
