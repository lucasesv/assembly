package br.com.lucasv.southsystem.assembly.infra.persistence.jpa.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import br.com.lucasv.southsystem.assembly.core.entity.Vote;

/**
 * <p>JPA entity to store a Session.
 * 
 * <p>Because the Core Entities of the application is isolated
 * from the persistence layer, it is necessary to crate
 * JPA entities wrappers to store the data of the Core Entities.
 * 
 * <p>The information about the fields can be found in
 * {@link br.com.lucasv.southsystem.assembly.core.entity.Session}.
 * 
 * @author Lucas Vasconcelos 
 * @see br.com.lucasv.southsystem.assembly.core.entity.Session
 *
 */
@Entity(name="Session")
public class SessionJpa {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;
  
  @OneToOne
  @JoinColumn(name="subject_id", referencedColumnName="id")
  private SubjectJpa subject;
  
  @Column(name="start_date_time")
  private LocalDateTime startDateTime;
  
  @Column(name="end_date_time")
  private LocalDateTime endDateTime;
  
//  @OneToMany(mappedBy="session", fetch=FetchType.LAZY)
  @Transient
  private List<Vote> votes;

  public SessionJpa() {
  }
  
  public SessionJpa(SubjectJpa subject, LocalDateTime startDateTime,
    LocalDateTime endDateTime) {
    this.subject = subject;
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public SubjectJpa getSubject() {
    return subject;
  }

  public void setSubject(SubjectJpa subject) {
    this.subject = subject;
  }

  public LocalDateTime getStartDateTime() {
    return startDateTime;
  }

  public void setStartDateTime(LocalDateTime startDateTime) {
    this.startDateTime = startDateTime;
  }

  public LocalDateTime getEndDateTime() {
    return endDateTime;
  }

  public void setEndDateTime(LocalDateTime endDateTime) {
    this.endDateTime = endDateTime;
  }

  public List<Vote> getVotes() {
    return votes;
  }

  public void setVotes(List<Vote> votes) {
    this.votes = votes;
  }
  
}
