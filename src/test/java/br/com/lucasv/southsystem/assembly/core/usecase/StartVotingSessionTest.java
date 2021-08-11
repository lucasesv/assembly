package br.com.lucasv.southsystem.assembly.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.lucasv.southsystem.assembly.adapter.persistence.SessionPersistenceAdapter;
import br.com.lucasv.southsystem.assembly.adapter.persistence.SubjectPersistenceAdapter;
import br.com.lucasv.southsystem.assembly.core.entity.Session;
import br.com.lucasv.southsystem.assembly.core.entity.Subject;

/**
 * <p>JUnit tests for the use case {@link StartVotingSession}.
 * 
 * <p>Obs: All methods should be self explanatory and the need of
 * javadoc means that the method may not be well written. 
 * 
 * @author Lucas Vasconcelos
 *
 */
@ExtendWith(MockitoExtension.class)
public class StartVotingSessionTest {

  private StartVotingSession startVotingSession;
  private Subject subject;
  
  @Mock
  private SubjectPersistenceAdapter subjectPersistenceAdapter;
  
  @Mock
  private SessionPersistenceAdapter sessionPersistenceAdapter;

  @BeforeEach
  void setup() {
    subject = new Subject(1, "Subject 1");
    lenient().when(subjectPersistenceAdapter.getSubject(1)).thenReturn(subject);
    lenient().when(sessionPersistenceAdapter.saveSession(any())).thenReturn(1);
    startVotingSession = new StartVotingSession(
        subjectPersistenceAdapter,
        sessionPersistenceAdapter);
  }

  @Test
  @DisplayName("Should start a voting session with default duration")
  void shouldStartVotingSessionWithDefaultDuration() {
    // Act
    Session session = startVotingSession.execute(subject.getId());
    LocalDateTime endDateTime = session.getEndDateTime();
    LocalDateTime startDateTime = session.getStartDateTime();
    LocalDateTime expectedEndDateTime = startDateTime.plusMinutes(Session.DEFAULT_DURATION);
    
    // Assert
    assertEquals(expectedEndDateTime, endDateTime, "Should have 1min of duration");
  }

  @Test
  @DisplayName("Should start a voting session with specific duration")
  void shouldStartVotingSessionWithSpecificDuration() {
    // Arrange
    int duration = 50;
    
    // Act
    Session session = startVotingSession.execute(subject.getId(), duration);
    LocalDateTime endDateTime = session.getEndDateTime();
    LocalDateTime startDateTime = session.getStartDateTime();
    LocalDateTime expectedEndDateTime = startDateTime.plusMinutes(duration);
    
    // Assert
    assertEquals(expectedEndDateTime, endDateTime, "Should have 50mins of duration");
  }

  @Test
  @DisplayName("Should not start a voting session with invalid session and/or duration")
  void shouldNotStartVotingSessionWithInvalidDuration() {
    // Arrange
    String errMsg = "Should throw IllegalArgumentException exception";
    
    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> startVotingSession.execute(0, 0), errMsg);
    assertThrows(IllegalArgumentException.class, () -> startVotingSession.execute(0, -1), errMsg);
    assertThrows(IllegalArgumentException.class, () -> startVotingSession.execute(1, 0), errMsg);
    assertThrows(IllegalArgumentException.class, () -> startVotingSession.execute(1, -1), errMsg);
  }

  @Test
  @DisplayName("Should session be open")
  void shouldSessionBeOpen() {
    // Arrange
    int sessionId = 1;
    int duration = 50;
    
    // Act
    Session session = startVotingSession.execute(sessionId, duration);
    
    // Assert
    assertTrue(session.isOpen(), "Session should be open but was closed");
  }

  @Test
  @DisplayName("Should session be closed")
  void shouldSessionBeClosed() {
    // Arrange
    int sessionId = 1;
    int duration = 50;
    
    // Act
    Session session = startVotingSession.execute(sessionId, duration);
    LocalDateTime startDateTime = LocalDateTime.now().minusMinutes(100);
    LocalDateTime endDateTime = LocalDateTime.now().minusMinutes(90);
    session.setStartDateTime(startDateTime);
    session.setEndDateTime(endDateTime);
    
    // Assert
    assertTrue(session.isClosed(), "Session should be closed but was open");
  }

}
