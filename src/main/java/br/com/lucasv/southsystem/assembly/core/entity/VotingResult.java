package br.com.lucasv.southsystem.assembly.core.entity;

import java.util.List;

/**
 * <p>A VotingResult is used to store the result of a voting.
 * It stores the session where the vote was taken. It also stores the
 * amount of yes and no votes in order to calculate the result of
 * the voting wich can be Approved, Rejectd or Draw.
 * 
 * @author Lucas Vasconcelos
 *
 */
public class VotingResult {

  private Session session;
  private List<Vote> yesVotes;
  private List<Vote> noVotes;

  /**
   * <p>Create a VotingResult with specified parameters.
   * 
   * @param session The session where the votes were taken.
   * @param yesVotes The list of yes votes.
   * @param noVotes The list of no votes.
   */
  public VotingResult(Session session, List<Vote> yesVotes, List<Vote> noVotes) {
    this.session = session;
    this.yesVotes = yesVotes;
    this.noVotes = noVotes;
  }

  /**
   * Retrieve the amount of yes votes.
   * 
   * @return The amount of yes votes.
   */
  public int getYesVotesAmount() {
    return yesVotes.size();
  }

  /**
   * Retrieve the amount of no votes.
   * 
   * @return The amount of no votes.
   */
  public int getNoVotesAmount() {
    return noVotes.size();
  }
  
  /**
   * Check if the voting result is approved.
   * 
   * @return True if the voting result is approved.
   */
  public boolean isApproved() {
    return yesVotes.size() > noVotes.size();
  }

  /**
   * Check if the voting result is rejected.
   * 
   * @return True if the voting result is rejected.
   */
  public boolean isRejected() {
    return yesVotes.size() < noVotes.size();
  }

  /**
   * Check if result of the voting ended in draw.
   * 
   * @return True if result of the voting ended in draw.
   */
  public boolean isDraw() {
    return yesVotes.size() == noVotes.size();
  }

  /**
   * @return the voting result's session
   */
  public Session getSession() {
    return session;
  }

  /**
   * @param session the session to set to the voting result
   */
  public void setSession(Session session) {
    this.session = session;
  }

  /**
   * @return the list of yes votes of the voting result
   */
  public List<Vote> getYesVotes() {
    return yesVotes;
  }

  /**
   * @param yesVotes the yesVotes to set to the voting result
   */
  public void setYesVotes(List<Vote> yesVotes) {
    this.yesVotes = yesVotes;
  }

  /**
   * @return the list of no votes of the voting result
   */
  public List<Vote> getNoVotes() {
    return noVotes;
  }

  /**
   * @param noVotes the noVotes to set to the voting result
   */
  public void setNoVotes(List<Vote> noVotes) {
    this.noVotes = noVotes;
  }

  /**
   * {@code VotingResult} equality is based on it's session id
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    VotingResult other = (VotingResult) obj;
    if (!session.equals(other.getSession()))
      return false;
    if (this == obj)
      return true;
    return true;
  }
}
