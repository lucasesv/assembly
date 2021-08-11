package br.com.lucasv.southsystem.assembly.infra.http.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * <p>The DTO with information about a Vote.
 * 
 * <p>This is design to exchange data through HTTP Controllers.
 * 
 * <p>More information about the meaning of the fields can be found in:
 * {@link br.com.lucasv.southsystem.assembly.core.entity.Vote}
 * 
 * @author Lucas Vasconcelos
 * @see br.com.lucasv.southsystem.assembly.core.entity.Vote
 */
public class VoteDto {

  @Positive
  private int sessionId;
  
  @Positive
  private int memberId;
  
  @NotNull
  private boolean choice;
  
  /**
   * Create a VoteDto with the specified parameters.
   *  
   * @param memberId The id of the member who voted.
   * @param sessionId The id of the session where the voted was registered.
   * @param choice The vote choice. {@code true} means yes.
   */
  public VoteDto(int memberId, int sessionId, boolean choice) {
    this.sessionId = sessionId;
    this.memberId = memberId;
    this.choice = choice;
  }

  public int getSessionId() {
    return sessionId;
  }

  public void setSessionId(int sessionId) {
    this.sessionId = sessionId;
  }

  public int getMemberId() {
    return memberId;
  }

  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  public boolean getChoice() {
    return choice;
  }

  public void setChoice(boolean choice) {
    this.choice = choice;
  }

  
}
