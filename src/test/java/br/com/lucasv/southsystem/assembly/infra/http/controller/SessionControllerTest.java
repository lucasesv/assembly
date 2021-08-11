package br.com.lucasv.southsystem.assembly.infra.http.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import br.com.lucasv.southsystem.assembly.core.entity.Member;
import br.com.lucasv.southsystem.assembly.core.entity.Session;
import br.com.lucasv.southsystem.assembly.core.entity.Subject;
import br.com.lucasv.southsystem.assembly.core.entity.Vote;
import br.com.lucasv.southsystem.assembly.core.entity.VotingResult;
import br.com.lucasv.southsystem.assembly.core.usecase.CalculateVotingResult;
import br.com.lucasv.southsystem.assembly.core.usecase.StartVotingSession;

/**
 * <p>Integration tests for Rest Controller {@link SessionController}.
 * 
 * <p>Obs: All methods should be self explanatory and the need of
 * javadoc means that the method may not be well written. 
 * 
 * @author Lucas Vasconcelos
 *
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTest {

  private Subject subject; 
  
  @Autowired
  private MockMvc mvc;
  
  @MockBean
  private StartVotingSession startVotingSession;
  
  @MockBean
  private CalculateVotingResult calculateVotingResult;
  
  @BeforeEach
  void setup() {
    subject = new Subject(1, "Subject 1");
  }
  
  @Test
  @DisplayName("Should start a voting session")
  void shouldStartVotingSession() throws Exception {
    // Arrange
    LocalDateTime startDateTime = LocalDateTime.of(2021, 8, 7, 19, 00, 00);
    LocalDateTime endDateTime = startDateTime.plusMinutes(10);
    Session session = new Session(1, subject, startDateTime, endDateTime);
    when(startVotingSession.execute(1, 50)).thenReturn(session);
    RequestBuilder request = post("/subjects/{subjectId}/sessions", subject.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content("{ \"duration\": 50 }");
    
    // Act & Assert
    String expectedBody = "{ \"id\": 1,"
        + "\"duration\": 50,"
        + "\"startDateTime\": \"2021-08-07T19:00:00\","
        + "\"endDateTime\": \"2021-08-07T19:10:00\"}";
    mvc.perform(request)
        .andExpect(status().isCreated())
        .andExpect(content().json(expectedBody));
  }
  
  @Test
  @DisplayName("Should not start a voting session with invalid Duration")
  void shouldNotStartVotingSessionInvalidDuration() throws Exception {
    // Arrange
    RequestBuilder request = post("/subjects/{subjectId}/sessions", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content("{ \"duration\": -1 }");
    
    // Act & Assert
    mvc.perform(request)
        .andExpect(status().isBadRequest());
  }
  
  @Test
  @DisplayName("Should not start a voting session of a non existent subject")
  void shouldNotStartVotingSessionNonExistentSubject() throws Exception {
    // Arrange
    RequestBuilder request = post("/subjects/{subjectId}/sessions", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content("{ \"duration\": -1 }");
    
    // Act & Assert
    mvc.perform(request)
        .andExpect(status().isBadRequest());
  }
  
  @Test
  @DisplayName("Should calculate approved voting result")
  void shouldCalculateApprovedVotingResult() throws Exception {
    // Arrange
    Session session = new Session(subject);
    List<Member> members = Arrays.asList(
        new Member(1),
        new Member(2),
        new Member(3));
    List<Vote> yesVotes = Arrays.asList(
        new Vote(session, members.get(0), true),
        new Vote(session, members.get(1), false));
    List<Vote> noVotes = Arrays.asList(
        new Vote(session, members.get(2), false));
    VotingResult votingResult = new VotingResult(session, yesVotes, noVotes);
    when(calculateVotingResult.execute(1)).thenReturn(votingResult);
    RequestBuilder request = get("/subjects/{subjectId}/sessions/{sessionId}/result", 1, 1)
        .accept(MediaType.APPLICATION_JSON);
    
    // Act & Assert
    mvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result").value("Approved"));
  }
  
  @Test
  @DisplayName("Should calculate rejected voting result")
  void shouldCalculateRejectedVotingResult() throws Exception {
    // Arrange
    Session session = new Session(subject);
    List<Member> members = Arrays.asList(
        new Member(1),
        new Member(2),
        new Member(3));
    List<Vote> yesVotes = Arrays.asList(
        new Vote(session, members.get(0), true));
    List<Vote> noVotes = Arrays.asList(
        new Vote(session, members.get(1), false),
        new Vote(session, members.get(2), false));
    VotingResult votingResult = new VotingResult(session, yesVotes, noVotes);
    when(calculateVotingResult.execute(1)).thenReturn(votingResult);
    RequestBuilder request = get("/subjects/{subjectId}/sessions/{sessionId}/result", 1, 1)
        .accept(MediaType.APPLICATION_JSON);
    
    // Act & Assert
    mvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result").value("Rejected"));
  }
  
  @Test
  @DisplayName("Should calculate rejected voting result")
  void shouldCalculateDrawVotingResult() throws Exception {
    // Arrange
    Subject subject = new Subject("Subject 1");
    Session session = new Session(subject);
    List<Member> members = Arrays.asList(
        new Member(1),
        new Member(2));
    List<Vote> yesVotes = Arrays.asList(
        new Vote(session, members.get(0), true));
    List<Vote> noVotes = Arrays.asList(
        new Vote(session, members.get(1), false));
    VotingResult votingResult = new VotingResult(session, yesVotes, noVotes);
    when(calculateVotingResult.execute(1)).thenReturn(votingResult);
    RequestBuilder request = get("/subjects/{subjectId}/sessions/{sessionId}/result", 1, 1)
        .accept(MediaType.APPLICATION_JSON);
    
    // Act & Assert
    mvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result").value("Draw"));
  }
  
}
