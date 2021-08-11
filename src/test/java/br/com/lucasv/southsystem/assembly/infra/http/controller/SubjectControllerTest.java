package br.com.lucasv.southsystem.assembly.infra.http.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

import br.com.lucasv.southsystem.assembly.core.entity.Subject;
import br.com.lucasv.southsystem.assembly.core.usecase.CreateSubject;
import br.com.lucasv.southsystem.assembly.infra.http.controller.SubjectController;

/**
 * <p>Integration tests for Rest Controller {@link SubjectController}.
 * 
 * <p>Obs: All methods should be self explanatory and the need of
 * javadoc means that the method may not be well written. 
 * 
 * @author Lucas Vasconcelos
 *
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(SubjectController.class)
public class SubjectControllerTest {

  @Autowired
  private MockMvc mvc;
  
  @MockBean
  private CreateSubject createSubject;
  
  @Test
  @DisplayName("Should create a Subject")
  void shouldCreateSubject() throws Exception {
    // Arrange
    when(createSubject.execute("Subject 1"))
        .thenReturn(new Subject(1, "Subject 1"));
    RequestBuilder request = post("/subjects")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content("{ \"description\": \"Subject 1\"}");
    
    // Act & Assert
    mvc.perform(request)
        .andExpect(content().json("{ \"id\": 1, \"description\": \"Subject 1\" }"))
        .andExpect(status().isCreated());
  }
  
  @Test
  @DisplayName("Should not create Subject with invalid request body")
  void shouldNotCreateSubjectInvalidRequestBody() throws Exception {
    // Arrange
    RequestBuilder request = post("/subjects")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON);
    
    // Act & Assert
    mvc.perform(request)
        .andExpect(status().isBadRequest());
    ;
  }
}
