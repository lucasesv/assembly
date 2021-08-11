package br.com.lucasv.southsystem.assembly.core.usecase;

import br.com.lucasv.southsystem.assembly.adapter.persistence.SubjectPersistenceAdapter;
import br.com.lucasv.southsystem.assembly.core.entity.Subject;

/**
 * <p>Use Case for creating a {@link Subject}.
 * 
 * @author Lucas Vasconcelos
 *
 */
public class CreateSubject {

  private final SubjectPersistenceAdapter subjectPersistenceAdapter;

  /**
   * Create a use case for creating a {@link Subject}
   * 
   * @param subjectPersistenceAdapter An adapter to manipulate
   *        stored information about subjects.
   */
  public CreateSubject(SubjectPersistenceAdapter subjectRepository) {
    this.subjectPersistenceAdapter = subjectRepository;
  }

  /**
   * <p>Create a {@link Subject}.
   * 
   * @param description The description of the subject. Must not be blank.
   * @return The subject created.
   * @throws IllegalArgumentException Thrown when the parameters are invalid.
   */
  public Subject execute(String description) throws IllegalArgumentException {
    validateInputs(description);
    
    Subject subject = new Subject(description);
    int subjectId = subjectPersistenceAdapter.saveSubject(subject);
    subject.setId(subjectId);
    return subject;
  }

  /**
   * Validate the inputs of the use case
   * 
   * @param description The description of the subject
   */
  private void validateInputs(String description) {
    validateDescription(description);
  }

  /**
   * Validate the description of the subject
   * 
   * @param description The description of the subject
   */
  private void validateDescription(String description) {
    if (description == null || description.isBlank())
      throw new IllegalArgumentException("Description must not be empty.");
  }
  
}
