package br.com.lucasv.southsystem.assembly.core.usecase;

import java.util.List;

import br.com.lucasv.southsystem.assembly.adapter.persistence.MemberPersistenceAdapter;
import br.com.lucasv.southsystem.assembly.adapter.persistence.SessionPersistenceAdapter;
import br.com.lucasv.southsystem.assembly.adapter.persistence.VotePersistenceAdapter;
import br.com.lucasv.southsystem.assembly.core.entity.Member;
import br.com.lucasv.southsystem.assembly.core.entity.Session;
import br.com.lucasv.southsystem.assembly.core.entity.Vote;
import br.com.lucasv.southsystem.assembly.core.exception.CannotVoteInClosedSessionException;
import br.com.lucasv.southsystem.assembly.core.exception.MemberAlreadyVotedException;

/**
 * <p>Use Case for registering a member's vote in a session. 
 * 
 * @author Lucas Vasconcelos
 *
 */
public class ComputeVote {

  private final SessionPersistenceAdapter sessionPersistenceAdapter;
  private final MemberPersistenceAdapter memberPersistenceAdapter;
  private final VotePersistenceAdapter votePersistenceAdapter;

  /**
   * Create a use case for registering a member's vote in a session.
   * 
   * @param sessionPersistenceAdapter An adapter to manipulate
   *        stored information about sessions.
   * @param memberPersistenceAdapter An adapter to manipulate
   *        stored information about members.
   * @param votePersistenceAdapter An adapter to manipulate stored
   *        information about votes.
   */
  public ComputeVote(
      SessionPersistenceAdapter sessionPersistenceAdapter,
      MemberPersistenceAdapter memberPersistenceAdapter,
      VotePersistenceAdapter votePersistenceAdapter) {

    this.sessionPersistenceAdapter = sessionPersistenceAdapter;
    this.memberPersistenceAdapter = memberPersistenceAdapter;
    this.votePersistenceAdapter = votePersistenceAdapter;
  }

  /**
   * <p>Register a member's vote in a session.
   * 
   * @param sessionId The session's id to register the vote. Must be greater than 0.
   * @param memberId The id of the member who took the vote. Must be greater than 0.
   * @param choice The vote itself. {@code true} means yes.
   * @throws IllegalArgumentException Thrown when the parameters are invalid.
   * @throws CannotVoteInClosedSessionException Thrown when trying to register the
   *        vote in a closed session.
   * @throws MemberAlreadyVotedException Thrown when trying to register the vote
   *        of a member who had already voted.
   */
  public void execute(int sessionId, int memberId, boolean choice)
      throws IllegalArgumentException,
      CannotVoteInClosedSessionException,
      MemberAlreadyVotedException {

    validateInputs(sessionId, memberId);

    Session session = sessionPersistenceAdapter.getSession(sessionId);
    Member member = memberPersistenceAdapter.getMember(memberId);
    validateVote(session, member);

    Vote vote = new Vote(session, member, choice);
    session.registerVote(vote);
    votePersistenceAdapter.saveVote(vote);
  }
  
  /**
   * Validate the inputs of the use case.
   * 
   * @param sessionId The id of the session.
   * @param memberId the id of the member.
   */
  private void validateInputs(int sessionId, int memberId) {
    validateSessionId(sessionId);
    validateMemberId(memberId);
  }

  /**
   * Validate the id of the session.
   * 
   * @param sessionId The id of the session.
   */
  private void validateSessionId(int sessionId) {
    if (sessionId <= 0)
      throw new IllegalArgumentException("Session Id must be greater than 0.");
  }

  /**
   * Validate the id of the member.
   * 
   * @param sessionId The id of the member.
   */
  private void validateMemberId(int memberId) {
    if (memberId <= 0)
      throw new IllegalArgumentException("Member Id must be greater than 0.");
  }

  /**
   * <p>Validate if the vote can be registered with the specified parameters.
   * 
   * @param session {@link Session} to register the vote.
   * @param member {@link Member} who voted.
   * @throws CannotVoteInClosedSessionException Thrown when trying to register the
   *        vote in a closed session.
   * @throws MemberAlreadyVotedException Thrown when trying to register the vote
   *        of a member who had already voted.
   */
  private void validateVote(Session session, Member member)
      throws CannotVoteInClosedSessionException, MemberAlreadyVotedException {

    if (session.isClosed())
      throw new CannotVoteInClosedSessionException(session.getId());

    if (hasMemberAlreadyVoted(session, member))
      throw new MemberAlreadyVotedException(member.getId());
  }

  /**
   * <p>Check if the member had already voted previously.
   * 
   * @param session {@link Session} to register the vote.
   * @param member {@link Member} who voted.
   * @return True if the member had already voted previously.
   */
  private boolean hasMemberAlreadyVoted(Session session, Member member) {
    List<Vote> votes = votePersistenceAdapter.getVotes(session.getId());
    return votes.stream()
        .filter((v) -> v.getMember().equals(member))
        .findAny()
        .isPresent();
  }

}
