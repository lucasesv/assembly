package br.com.lucasv.southsystem.assembly.core.entity;

/**
 * <p>A Vote that members can take for a subject
 * 
 * Votes are meant to approve or reject a subject. 
 * Members can vote either {@code yes} or {@code no}
 * to approve or rejected subjects they are interested on.
 * 
 * 
 * @author Lucas Vasconcelos
 *
 */
public class Vote {

  private int id;
  private Session session;
  private Member member;
  private boolean choice;

  /**
   * <p> Create a Vote with specified parameters.
   * 
   * @param session The session that the member registered his vote.
   * @param member The member who took the vote.
   * @param choice The vote choice. {@code true} means yes.
   */
  public Vote(Session session, Member member, boolean choice) {
    this.session = session;
    this.member = member;
    this.choice = choice;
  }

  /**
   * <p> Create a Vote with specified parameters
   *
   * @param id The id of the Member.
   * @param session The session that the member registered his vote.
   * @param member The member who took the vote.
   * @param choice The vote choice. {@code true} means yes.
   */
  public Vote(int id, Session session, Member member, boolean choice) {
    this.id = id;
    this.session = session;
    this.member = member;
    this.choice = choice;
  }

  /**
   * <p>Check if the meber voted yes
   * 
   * @return True if the member voted yes
   */
  public boolean isYesChoice() {
    return choice;
  }

  /**
   * <p>Check if the meber voted no
   * 
   * @return True if the member voted no
   */
  public boolean isNoChoice() {
    return !choice;
  }

  /**
   * @return the Vote's id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * @return the Vote's session
   */
  public Session getSession() {
    return session;
  }

  /**
   * @param session the session to set
   */
  public void setSession(Session session) {
    this.session = session;
  }

  /**
   * @return the Vote's member
   */
  public Member getMember() {
    return member;
  }

  /**
   * @param member the member to set
   */
  public void setMember(Member member) {
    this.member = member;
  }

  /**
   * @return the Vote's choice
   */
  public boolean getChoice() {
    return choice;
  }

  /**
   * @param choice the choice to set
   */
  public void setChoice(boolean choice) {
    this.choice = choice;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Vote other = (Vote) obj;
    if (id != other.id)
      return false;
    if (this == obj)
      return true;
    return true;
  }
  
}
