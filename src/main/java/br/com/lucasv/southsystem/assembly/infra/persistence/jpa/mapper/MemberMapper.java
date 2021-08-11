package br.com.lucasv.southsystem.assembly.infra.persistence.jpa.mapper;

import org.springframework.stereotype.Component;

import br.com.lucasv.southsystem.assembly.core.entity.Member;
import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.entity.MemberJpa;

/**
 * <p>Member Mapper between Core Entity and JPA Entity. 
 * 
 * <p>Used to convert a Core Entity into a JPA Entity and vice versa.
 * 
 * @author Lucas Vasconcelos
 *
 */
@Component
public class MemberMapper {

  /**
   * <p>Convert a Member Jpa into a Member Core Entity.
   * 
   * @param memberJpa The MemberJpa which will be converted.
   * @return The Member Core Entity.
   */
  public Member mapToCore(MemberJpa memberJpa) {
    return new Member(memberJpa.getId());
  }
  
  /**
   * <p>Convert a Member Jpa into a Member Core Entity.
   * <p><b>IMPORTANT:</b> The id is not copied from the Core Entity to the JPA Entity.
   * 
   * @param memberJpa The MemberJpa which will be converted.
   * @return The Member Core Entity.
   */
  public MemberJpa mapToJpa(Member member) {
    return new MemberJpa();
  }
}
