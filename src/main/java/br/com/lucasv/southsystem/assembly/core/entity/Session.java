package br.com.lucasv.southsystem.assembly.core.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>A Session for a subject.
 * 
 * <p>Sessions are created so that the members
 * can vote for approving or rejecting a subject.
 * Members can vote in open sessions only.
 * 
 * @author Lucas Vasconcelos
 *
 */
public class Session {

  /**
   * The default duration of a Session. {@code DEFAULT_DURATION} in minutes is
   * assumed when no duration is specified.
   */
  public static final int DEFAULT_DURATION = 1;

  private int id;
  private Subject subject;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
  private List<Vote> votes;

  /**
   * <p>Create Session for a specific subject.
   * Takes the {@code @DEFAULT_DURATION} as the duration in minutes.
   * 
   * @param subject The subject bounded to the session
   */
  public Session(Subject subject) {
    initWithDuration(subject, DEFAULT_DURATION);
  }

  /**
   * <p>Create Session for a specific subject.
   * 
   * @param subject The subject bounded to the session.
   * @param duration The duration in minutes of the session.
   */
  public Session(Subject subject, int duration) {
    initWithDuration(subject, duration);
  }
  
  /**
   * <p>Create Session with specific parameters.
   * 
   * @param id The Session id.
   * @param subject The subject bounded to the Session .
   * @param startDateTime When the session should start.
   * @param endDateTime When the session should start.
   */
  public Session(int id, Subject subject, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    this.id = id;
    this.subject = subject;
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
    this.votes = new ArrayList<>();
  }
  
  /**
   * <p>Handy method to initialize the Session's fields.
   * Its useful in constructors to avoid code repetition.
   * 
   * @param subject The subject bounded to the session.
   * @param duration The duration in minutes of the session.
   */
  private void initWithDuration(Subject subject, int duration) {
    this.subject = subject;
    this.startDateTime = LocalDateTime.now();
    this.endDateTime = startDateTime.plusMinutes(duration);
    this.votes = new ArrayList<>();
  }

  /**
   * <p>Check if the Session is open for voting
   * 
   * @return True if the Session is open for voting. Otherwise returns false.
   */
  public boolean isOpen() {
    LocalDateTime now = LocalDateTime.now();
    return !now.isBefore(startDateTime) && !now.isAfter(endDateTime);
  }

  /**
   * <p>Check if the Session is closed for voting
   * 
   * @return False if the Session is closed for voting. Otherwise returns true.
   */
  public boolean isClosed() {
    return !isOpen();
  }

  /**
   * @return the Session's id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id the id to set to the Session
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * @return the Session's subject
   */
  public Subject getSubject() {
    return subject;
  }

  /**
   * @param subject the subject to set to the Session
   */
  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  /**
   * @return the Session's startDateTime
   */
  public LocalDateTime getStartDateTime() {
    return startDateTime;
  }

  /**
   * @param startDateTime the startDateTime to set to the Session
   */
  public void setStartDateTime(LocalDateTime startDateTime) {
    this.startDateTime = startDateTime;
  }

  /**
   * @return the Session's endDateTime
   */
  public LocalDateTime getEndDateTime() {
    return endDateTime;
  }

  /**
   * @param endDateTime the endDateTime to set to the Session
   */
  public void setEndDateTime(LocalDateTime endDateTime) {
    this.endDateTime = endDateTime;
  }

  /**
   * Register a vote in the Session
   * 
   * @param vote The vote to register in the Session
   */
  public void registerVote(Vote vote) {
    votes.add(vote);
  }

  /**
   * <p>Get the total votes in session
   * 
   * @return The total votes registered in the session
   */
  public int getTotalVotes() {
    return votes.size();
  }

  /**
   * Get all votes registered in the session
   * 
   * @return A list with all votes registered in the session
   */
  public List<Vote> getVotes() {
    return this.votes;
  }

  /**
   * Session equality is based on it's id
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Session other = (Session) obj;
    if (id != other.id)
      return false;
    if (this == obj)
      return true;
    return true;
  }

}
