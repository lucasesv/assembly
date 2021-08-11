package br.com.lucasv.southsystem.assembly.adapter.persistence;

import java.util.List;

import br.com.lucasv.southsystem.assembly.core.entity.Vote;

/**
 * <p>A Persistence Adapter to manipulate stored data about Vote.
 * 
 * <p>It is an Persistence Abastraction Layer so that the core
 * use cases can be decoupled of the persistence infrastructure.
 * 
 * @author Lucas Vasconcelos
 *
 */
public interface VotePersistenceAdapter {
  
  int saveVote(Vote vote);

  List<Vote> getVotes(int SessionId);
  
}
