package br.com.lucasv.southsystem.assembly.core.usecase;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.lucasv.southsystem.assembly.adapter.persistence.SubjectPersistenceAdapter;
import br.com.lucasv.southsystem.assembly.core.entity.Subject;
import br.com.lucasv.southsystem.assembly.core.usecase.CreateSubject;

/**
 * <p>JUnit tests for the use case {@link CreateSubject}.
 * 
 * <p>Obs: All methods should be self explanatory and the need of
 * javadoc means that the method may not be well written. 
 * 
 * @author Lucas Vasconcelos
 *
 */
@SuppressWarnings("unused")
@ExtendWith(MockitoExtension.class)
public class CreateSubjectTest {

  private CreateSubject createSubject;
  
  @Mock
  SubjectPersistenceAdapter subjectPersistenceAdapter;

  @BeforeEach
  void setup() {
    createSubject = new CreateSubject(subjectPersistenceAdapter);
  }

  @Test
  @DisplayName("Should create subject")
  void shouldCreateSubject() {
    // Arrange
    String subjectDescription = "Description";
    when(subjectPersistenceAdapter.saveSubject(any())).thenReturn(1);
    
    // Act
    Subject createdSubject = createSubject.execute(subjectDescription);
    
    // Assert
    assertEquals(
        1,
        createdSubject.getId(),
        "Subject id should be 1 but was " + createdSubject.getId());
    assertEquals(
        subjectDescription,
        createdSubject.getDescription(),
        "Description should be 'Description' but was " + createdSubject.getDescription());
  }

  @Test
  @DisplayName("Should create invalid subject")
  void shouldNotCreateInvalidSubject() {
    // Arrange
    String errMsg = "Should throw IllegalArgumentException exception";;
    assertThrows(IllegalArgumentException.class, () -> createSubject.execute(""), errMsg);
    assertThrows(IllegalArgumentException.class, () -> createSubject.execute("  "), errMsg);
    assertThrows(IllegalArgumentException.class, () -> createSubject.execute(null), errMsg);
  }

}
