package br.com.lucasv.southsystem.assembly.core.usecase;

import java.util.List;
import java.util.stream.Collectors;

import br.com.lucasv.southsystem.assembly.adapter.persistence.SessionPersistenceAdapter;
import br.com.lucasv.southsystem.assembly.adapter.persistence.VotePersistenceAdapter;
import br.com.lucasv.southsystem.assembly.core.entity.Session;
import br.com.lucasv.southsystem.assembly.core.entity.Vote;
import br.com.lucasv.southsystem.assembly.core.entity.VotingResult;

/**
 * <p>Use Case for Calculating the Voting Result of a session.
 * 
 * @author Lucas Vasconcelos
 *
 */
public class CalculateVotingResult {

  private final VotePersistenceAdapter votePersistenceAdapter;
  private final SessionPersistenceAdapter sessionPersistenceAdapter;

  /**
   * Create a use case for calculating the result vote of a session.
   * 
   * @param votePersistenceAdapter An adapter to manipulate stored
   *        information about votes.
   * @param sessionPersistenceAdapter An adapter to manipulate
   *        stored information about sessions.
   */
  public CalculateVotingResult(
      VotePersistenceAdapter votePersistenceAdapter,
      SessionPersistenceAdapter sessionPersistenceAdapter) {

    this.votePersistenceAdapter = votePersistenceAdapter;
    this.sessionPersistenceAdapter = sessionPersistenceAdapter;
  }

  /**
   * <p>Calculate the result of a voting.
   * It takes the a session and generates a {@link VotingResult}.
   * 
   * @param sessionId The session for calculating the voting result. Must be greater than 0.
   * @return A {@link VotingResult} with information about the voting result
   * @throws IllegalArgumentException Thrown when the parameters are invalid.
   */
  public VotingResult execute(int sessionId) throws IllegalArgumentException {
    validateInputs(sessionId);
    
    Session session = sessionPersistenceAdapter.getSession(sessionId);
    List<Vote> votes = votePersistenceAdapter.getVotes(session.getId());

    List<Vote> yesVotes = votes.stream()
        .filter((v) -> v.isYesChoice())
        .collect(Collectors.toList());

    List<Vote> noVotes = votes.stream()
        .filter((v) -> v.isNoChoice())
        .collect(Collectors.toList());

    return new VotingResult(session, yesVotes, noVotes);
  }

  /**
   * Validate the inputs of the use case
   * 
   * @param sessionId The id of the session
   */
  private void validateInputs(int sessionId) {
    validateSessionId(sessionId);
  }

  /**
   * Validate the id of the session
   * 
   * @param sessionId The id of the session
   */
  private void validateSessionId(int sessionId) {
    if (sessionId <= 0)
      throw new IllegalArgumentException("Session Id must be greater than 0.");
  }

}
