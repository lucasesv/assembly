package br.com.lucasv.southsystem.assembly.infra.persistence.jpa.mapper;

import org.springframework.stereotype.Component;

import br.com.lucasv.southsystem.assembly.core.entity.Member;
import br.com.lucasv.southsystem.assembly.core.entity.Session;
import br.com.lucasv.southsystem.assembly.core.entity.Vote;
import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.entity.MemberJpa;
import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.entity.SessionJpa;
import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.entity.VoteJpa;

/**
 * <p>Vote Mapper between Core Entity and JPA Entity. 
 * 
 * <p>Used to convert a Core Entity into a JPA Entity and vice versa.
 * 
 * @author Lucas Vasconcelos
 *
 */
@Component
public class VoteMapper {
  
  private final SessionMapper sessionMapper;
  private final MemberMapper memberMapper;
  
  public VoteMapper(SessionMapper sessionMapper, MemberMapper memberMapper) {
    this.sessionMapper = sessionMapper;
    this.memberMapper = memberMapper;
  }

  /**
   * <p>Convert a Vote Jpa into a Vote Core Entity.
   * 
   * @param voteJpa The VoteJpa which will be converted.
   * @return The Vote Core Entity.
   */
  public Vote mapToCore(VoteJpa voteJpa) {
    Session session = sessionMapper.mapToCore(voteJpa.getSession());
    Member member = memberMapper.mapToCore(voteJpa.getMember());
    return new Vote(
        voteJpa.getId(),
        session,
        member,
        voteJpa.getChoice());
  }
  
  /**
   * <p>Convert a Vote Jpa into a Vote Core Entity.
   * <p><b>IMPORTANT:</b> The id is not copied from the Core Entity to the JPA Entity.
   * 
   * @param voteJpa The VoteJpa which will be converted.
   * @return The Vote Core Entity.
   */
  public VoteJpa mapToJpa(Vote vote) {
    SessionJpa sessionJpa = sessionMapper.mapToJpa(vote.getSession());
    sessionJpa.setId(vote.getSession().getId());
    MemberJpa memberJpa = memberMapper.mapToJpa(vote.getMember());
    memberJpa.setId(vote.getMember().getId());
    return new VoteJpa(
        sessionJpa,
        memberJpa,
        vote.getChoice());
  }
}
