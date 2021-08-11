package br.com.lucasv.southsystem.assembly.infra.http.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.oneOf;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.NoSuchElementException;

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

import br.com.lucasv.southsystem.assembly.infra.http.dto.MemberVotingStatusDto;

/**
 * <p>Integration tests for Rest Controller {@link MemberController}.
 * 
 * <p>Obs: All methods should be self explanatory and the need of
 * javadoc means that the method may not be well written. 
 * 
 * @author Lucas Vasconcelos
 *
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberController.class)
public class MemberControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  MemberVotingStatusProxy memberVotingStatusProxy;
  
  @Test
  @DisplayName("Should retrieve the member voting status")
  void shouldRetrieveMemberVotingStatus() throws Exception {
    // Arrange
    String cpf = "65477706058";
    MemberVotingStatusDto memberVotingStatusDto = new MemberVotingStatusDto("ABLE_TO_VOTE");
    when(memberVotingStatusProxy.getMemberStatusVote(cpf)).thenReturn(memberVotingStatusDto);
    RequestBuilder request = get("/members/{cpf}/member-status", cpf)
        .accept(MediaType.APPLICATION_JSON);
    
    // Act & Assert
    mvc.perform(request)
        .andDo(print())
        .andExpect(jsonPath("$.status", is(oneOf("ABLE_TO_VOTE", "UNABLE_TO_VOTE"))))
        .andExpect(status().isOk());
  }
  
  @Test
  @DisplayName("Should not retrieve the member voting status with wrong cpf")
  void shouldNotRetrieveMemberVotingStatusWithWrongCpf() throws Exception {
    // Arrange
    String cpf = "00000000000";
    doThrow(new NoSuchElementException("CPF not found."))
        .when(memberVotingStatusProxy)
        .getMemberStatusVote(cpf);
    RequestBuilder request = get("/members/{cpf}/member-status", "00000000000");
    
    // Act & Assert
    mvc.perform(request).andExpect(status().isNotFound());
  }
}
