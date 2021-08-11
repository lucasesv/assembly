package br.com.lucasv.southsystem.assembly.adapter.persistence;

import java.util.NoSuchElementException;

import br.com.lucasv.southsystem.assembly.core.entity.Session;

/**
 * <p>A Persistence Adapter to manipulate stored data about Session.
 * 
 * <p>It is an Persistence Abastraction Layer so that the core
 * use cases can be decoupled of the persistence infrastructure.
 * 
 * @author Lucas Vasconcelos
 *
 */
public interface SessionPersistenceAdapter {

  int saveSession(Session session);
  
  Session getSession(int sessionId) throws NoSuchElementException;
  
}
