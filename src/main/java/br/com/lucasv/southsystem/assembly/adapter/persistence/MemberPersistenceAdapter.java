package br.com.lucasv.southsystem.assembly.adapter.persistence;

import br.com.lucasv.southsystem.assembly.core.entity.Member;

/**
 * <p>A Persistence Adapter to manipulate stored data about Member.
 * 
 * <p>It is an Persistence Abastraction Layer so that the core
 * use cases can be decoupled of the persistence infrastructure.
 * 
 * @author Lucas Vasconcelos
 *
 */
public interface MemberPersistenceAdapter {

  int saveMember(Member member);
  
  Member getMember(int memberId);
  
}
