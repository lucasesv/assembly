package br.com.lucasv.southsystem.assembly.infra.persistence.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * <p>JPA entity to store a Vote.
 * 
 * <p>Because the Core Entities of the application is isolated
 * from the persistence layer, it is necessary to crate
 * JPA entities wrappers to store the data of the Core Entities.
 * 
 * <p>The information about the fields can be found in
 * {@link br.com.lucasv.southsystem.assembly.core.entity.Vote}.
 * 
 * @author Lucas Vasconcelos 
 * @see br.com.lucasv.southsystem.assembly.core.entity.Vote
 *
 */
@Entity(name="Vote")
public class VoteJpa {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
  
  @OneToOne
  @JoinColumn(name="session_id", referencedColumnName="id")
  private SessionJpa session;
  
  @OneToOne
  @JoinColumn(name="member_id", referencedColumnName="id")
  private MemberJpa member;
  
  private boolean choice;

  public VoteJpa() {
  }
  
  public VoteJpa(SessionJpa session, MemberJpa member, boolean choice) {
    this.session = session;
    this.member = member;
    this.choice = choice;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public SessionJpa getSession() {
    return session;
  }

  public void setSession(SessionJpa session) {
    this.session = session;
  }

  public MemberJpa getMember() {
    return member;
  }

  public void setMember(MemberJpa member) {
    this.member = member;
  }

  public boolean getChoice() {
    return choice;
  }

  public void setChoice(boolean choice) {
    this.choice = choice;
  }
  
  
}
