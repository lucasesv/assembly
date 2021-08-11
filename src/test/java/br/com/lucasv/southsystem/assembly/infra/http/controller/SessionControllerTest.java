package br.com.lucasv.southsystem.assembly.infra.http.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

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

import br.com.lucasv.southsystem.assembly.core.entity.Session;
import br.com.lucasv.southsystem.assembly.core.entity.Subject;
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
  
}
