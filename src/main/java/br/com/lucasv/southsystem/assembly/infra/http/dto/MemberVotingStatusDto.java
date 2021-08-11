package br.com.lucasv.southsystem.assembly.infra.http.dto;

/**
 * <p>The DTO with information about
 * if a member is either able or unable to vote in a session.
 * 
 * <p>This class was only designed to meet the requirements of
 * the Bonus 1 of the challenge. It is not part of the application
 * itself. 
 * 
 * @author Lucas Vasconcelos
 *
 */
public class MemberVotingStatusDto {

  private String status;

  public MemberVotingStatusDto() {
  }
  
  /**
   * <p>Create the DTO with the status specified.
   * 
   * @param status The status with states if the member
   *        is able to vote in the session specified in the request.
   */
  public MemberVotingStatusDto(String status) {
    this.status = status;
  }

  /**
   * Return if the member is able to vote.
   * @return The status about if the member is able to vote in the session.
   *        Possible status comes from external API and it can be:
   *        ABLE_TO_VOTE, UNABLE_TO_VOTE.
   */
  public String getStatus() {
    return status;
  }

  /**
   * Sets the new status
   * @param status The new status.
   */
  public void setStatus(String status) {
    this.status = status;
  }
  
}
