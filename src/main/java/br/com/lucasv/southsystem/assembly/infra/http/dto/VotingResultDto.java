package br.com.lucasv.southsystem.assembly.infra.http.dto;

/**
 * <p>The DTO with information about a VotingResult.
 * 
 * <p>This is design to exchange data through HTTP Controllers.
 * 
 * <p>More information about the meaning of the fields can be found in:
 * {@link br.com.lucasv.southsystem.assembly.core.entity.VotingResult}
 * 
 * @author Lucas Vasconcelos
 * @see br.com.lucasv.southsystem.assembly.core.entity.VotingResult
 */
public class VotingResultDto {
  
  public static final String APPROVED = "Approved";
  public static final String REJECTED = "Rejected";
  public static final String DRAW = "Draw";

  private int yesVotesAmount;
  private int noVotesAmount;
  private String result;

  /**
   * <p>Create a VotingResultDto with the specified parameters.
   * 
   * @param yesVotes The list of yes votes.
   * @param noVotes The list of no votes.
   */
  public VotingResultDto(int yesVotesAmount, int noVotesAmount) {
    this.yesVotesAmount = yesVotesAmount;
    this.noVotesAmount = noVotesAmount;
    this.result = calculateResult();
  }

  /**
   * Return if the voting result is approved, rejected or draw.
   * 
   * @return The status of the voting result. Possible values are:
   *        "Approved", "Rejected", "Draw".
   */
  private String calculateResult() {
    if (yesVotesAmount > noVotesAmount)
      return APPROVED;
    if (yesVotesAmount < noVotesAmount)
      return REJECTED;
    return DRAW;
  }

  /**
   * Retrieve the amount of yes votes
   * 
   * @return The amount of yes votes
   */
  public int getYesVotesAmount() {
    return yesVotesAmount;
  }

  public void setYesVotesAmount(int yesVotesAmount) {
    this.yesVotesAmount = yesVotesAmount;
  }

  /**
   * Retrieve the amount of no votes
   * 
   * @return The amount of no votes
   */
  public int getNoVotesAmount() {
    return noVotesAmount;
  }

  public void setNoVotesAmount(int noVotesAmount) {
    this.noVotesAmount = noVotesAmount;
  }

  /**
   * Get the result of the voting.
   * 
   * @return The result of the voting: "Approved", "Rejected", "Draw".
   */
  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }
  
}
