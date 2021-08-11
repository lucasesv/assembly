package br.com.lucasv.southsystem.assembly.core.usecase;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.lucasv.southsystem.assembly.adapter.persistence.SessionPersistenceAdapter;
import br.com.lucasv.southsystem.assembly.adapter.persistence.VotePersistenceAdapter;
import br.com.lucasv.southsystem.assembly.core.entity.Member;
import br.com.lucasv.southsystem.assembly.core.entity.Session;
import br.com.lucasv.southsystem.assembly.core.entity.Subject;
import br.com.lucasv.southsystem.assembly.core.entity.Vote;
import br.com.lucasv.southsystem.assembly.core.entity.VotingResult;

/**
 * <p>JUnit tests for the use case {@link CalculateVotingResult}.
 * 
 * <p>Obs: All methods should be self explanatory and the need of
 * javadoc means that the method may not be well written. 
 * 
 * @author Lucas Vasconcelos
 *
 */
@ExtendWith(MockitoExtension.class)
public class CalculateVotingResultTest {

  private Subject subject;
  private Session session;
  private CalculateVotingResult calculateVotingResult;
  
  @Mock
  private VotePersistenceAdapter votePersistenceAdapter;
  
  @Mock
  private SessionPersistenceAdapter sessionPersistenceAdapter;
    
  @BeforeEach
  void setup() {
    calculateVotingResult = new CalculateVotingResult(
        votePersistenceAdapter,
        sessionPersistenceAdapter);
    subject = new Subject(1, "Description");
    session = new Session(subject, Session.DEFAULT_DURATION);
    session.setId(1);
    when(sessionPersistenceAdapter.getSession(1)).thenReturn(session);
  }
  
  @Test
  @DisplayName("Should voting session be approved")
  void shouldVotingResultBeApproved() {
    // Arrange
    List<Vote> votes = Arrays.asList(
        new Vote(session, new Member(1), true),
        new Vote(session, new Member(2), true),
        new Vote(session, new Member(3), false));
    when(votePersistenceAdapter.getVotes(session.getId())).thenReturn(votes);
    
    // Act
    VotingResult votingResult = calculateVotingResult.execute(session.getId());
    
    // Assert
    String errMsg = "Voting result should be approved";
    assertTrue(votingResult.isApproved(), errMsg);
  }
  
  @Test
  @DisplayName("Should voting session be rejected")
  void shouldVotingResultBeRejected() {
    // Arrange
    List<Vote> votes = Arrays.asList(
        new Vote(session, new Member(1), true),
        new Vote(session, new Member(2), false),
        new Vote(session, new Member(3), false));
    when(votePersistenceAdapter.getVotes(session.getId())).thenReturn(votes);
    
    // Act
    VotingResult votingResult = calculateVotingResult.execute(session.getId());
    
    // Assert
    String errMsg = "Voting result should be rejected";
    assertTrue(votingResult.isRejected(), errMsg);
  }
  
  @Test
  @DisplayName("Should voting session be rejected")
  void shouldVotingResultBeDraw() {
    // Arrange
    List<Vote> votes = Arrays.asList(
        new Vote(session, new Member(1), true),
        new Vote(session, new Member(2), false));
    when(votePersistenceAdapter.getVotes(session.getId())).thenReturn(votes);
    
    // Act
    VotingResult votingResult = calculateVotingResult.execute(session.getId());
    
    // Assert
    String errMsg = "Voting result should be draw";
    assertTrue(votingResult.isDraw(), errMsg);
  }
  
}
