package br.com.lucasv.southsystem.assembly.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.lucasv.southsystem.assembly.adapter.persistence.MemberPersistenceAdapter;
import br.com.lucasv.southsystem.assembly.adapter.persistence.SessionPersistenceAdapter;
import br.com.lucasv.southsystem.assembly.adapter.persistence.VotePersistenceAdapter;
import br.com.lucasv.southsystem.assembly.core.entity.Member;
import br.com.lucasv.southsystem.assembly.core.entity.Session;
import br.com.lucasv.southsystem.assembly.core.entity.Subject;
import br.com.lucasv.southsystem.assembly.core.entity.Vote;
import br.com.lucasv.southsystem.assembly.core.exception.CannotVoteInClosedSessionException;
import br.com.lucasv.southsystem.assembly.core.exception.MemberAlreadyVotedException;

/**
 * <p>JUnit tests for the use case {@link ComputeVote}.
 * 
 * <p>Obs: All methods should be self explanatory and the need of
 * javadoc means that the method may not be well written. 
 * 
 * @author Lucas Vasconcelos
 *
 */
@ExtendWith(MockitoExtension.class)
public class ComputeVoteTest {

  private ComputeVote computeVote;
  private Subject subject;
  
  @Mock
  private SessionPersistenceAdapter sessioPersistenceAdapter;
  
  @Mock
  private VotePersistenceAdapter votePersistenceAdapter;
  
  @Mock
  private MemberPersistenceAdapter memberPersistenceAdapter;
  
  @Mock
  private StartVotingSession startVotingSession;
  
  
  @BeforeEach
  void setup() {
    computeVote = new ComputeVote(
        sessioPersistenceAdapter,
        memberPersistenceAdapter,
        votePersistenceAdapter);
    subject = new Subject(1, "Description");
    
    when(memberPersistenceAdapter.getMember(1)).thenReturn(new Member(1));
    lenient().when(memberPersistenceAdapter.getMember(2)).thenReturn(new Member(2));
    lenient().when(memberPersistenceAdapter.getMember(3)).thenReturn(new Member(3));
  }
  
  @Test
  @DisplayName("Should vote in open session")
  void shouldVoteInOpenSession() {
    // Arrange
    Session session = new Session(subject, Session.DEFAULT_DURATION);
    session.setId(1);
    when(sessioPersistenceAdapter.getSession(session.getId())).thenReturn(session);
    
    // Act
    computeVote.execute(session.getId(), 1, true);
    computeVote.execute(session.getId(), 2, false);
    computeVote.execute(session.getId(), 3, true);
    
    // Assert
    String errMsg = "Session should have 3 votes but had " + session.getTotalVotes();
    assertEquals(3, session.getTotalVotes(), errMsg);
  }
  
  @Test
  @DisplayName("Should not vote in closed session")
  void shouldNotVoteInClosedSession() throws InterruptedException {
    // Arrange
    Session session = new Session(subject, Session.DEFAULT_DURATION);
    session.setId(1);
    LocalDateTime startDateTime = LocalDateTime.now().minusMinutes(100);
    LocalDateTime endDateTime = LocalDateTime.now().minusMinutes(90);
    session.setStartDateTime(startDateTime);
    session.setEndDateTime(endDateTime);
    when(sessioPersistenceAdapter.getSession(session.getId())).thenReturn(session);
    
    // Act & Assert
    String errMsg = "Should throw CannotVoteInClosedSessionException exception";
    assertThrows(
        CannotVoteInClosedSessionException.class,
        () -> computeVote.execute(session.getId(), 1, true),
        errMsg);
  }
  
  @Test
  @DisplayName("Should a member not vote twice in a session")
  void shouldNotVoteTwice() throws InterruptedException {
    // Arrange
    Session session = new Session(subject, Session.DEFAULT_DURATION);
    session.setId(1);
    List<Vote> votes = Arrays.asList(new Vote(session, new Member(1), true));
    when(sessioPersistenceAdapter.getSession(session.getId())).thenReturn(session);
    when(votePersistenceAdapter.getVotes(session.getId())).thenReturn(votes);
    
    // Act & Assert
    String errMsg = "Should throw MemberAlreadyVotedException exception";
    assertThrows(
        MemberAlreadyVotedException.class,
        () -> computeVote.execute(session.getId(), 1, true),
        errMsg);
  }
  
}
