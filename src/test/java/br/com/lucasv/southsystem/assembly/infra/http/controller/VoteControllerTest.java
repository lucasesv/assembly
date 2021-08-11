package br.com.lucasv.southsystem.assembly.infra.http.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import br.com.lucasv.southsystem.assembly.core.exception.CannotVoteInClosedSessionException;
import br.com.lucasv.southsystem.assembly.core.exception.MemberAlreadyVotedException;
import br.com.lucasv.southsystem.assembly.core.usecase.ComputeVote;
import br.com.lucasv.southsystem.assembly.infra.http.controller.VoteController;

/**
 * <p>Integration tests for Rest Controller {@link VoteController}.
 * 
 * <p>Obs: All methods should be self explanatory and the need of
 * javadoc means that the method may not be well written. 
 * 
 * @author Lucas Vasconcelos
 *
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(VoteController.class)
public class VoteControllerTest {

  @Autowired
  private MockMvc mvc;
  
  @MockBean
  private ComputeVote computeVote;
  
  @Test
  @DisplayName("Should vote in open session")
  void shouldVoteInOpenSession() throws Exception {
    // Arrange
    doNothing().when(computeVote).execute(1, 1, true);
    RequestBuilder request = post("/subjects/{subjectId}/sessions/{sessionId}/votes", 1, 1)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content("{ \"sessionId\": 1, \"memberId\": 1, \"choice\": true }");
    
    // Act & Assert
    mvc.perform(request)
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
  }
  
  @Test
  @DisplayName("Should not vote in closed session")
  void shouldNotVoteInClosedSession() throws Exception {
    // Arrange
    doThrow(new CannotVoteInClosedSessionException(1)).when(computeVote).execute(1, 1, true);
    RequestBuilder request = post("/subjects/{subjectId}/sessions/{sessionId}/votes", 1, 1)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content("{ \"sessionId\": 1, \"memberId\": 1, \"choice\": true }");
    
    // Act & Assert
    mvc.perform(request)
        .andExpect(status().isBadRequest());
  }
  
  @Test
  @DisplayName("Should not vote twice")
  void shouldNotVoteTwice() throws Exception {
    // Arrange
    doThrow(new MemberAlreadyVotedException(1)).when(computeVote).execute(1, 1, true);
    RequestBuilder request = post("/subjects/{subjectId}/sessions/{sessionId}/votes", 1, 1)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content("{ \"sessionId\": 1, \"memberId\": 1, \"choice\": true }");
    
    // Act & Assert
    mvc.perform(request)
        .andExpect(status().isBadRequest());
  }
  
}
