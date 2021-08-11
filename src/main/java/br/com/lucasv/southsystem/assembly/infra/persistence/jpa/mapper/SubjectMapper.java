package br.com.lucasv.southsystem.assembly.infra.persistence.jpa.mapper;

import org.springframework.stereotype.Component;

import br.com.lucasv.southsystem.assembly.core.entity.Subject;
import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.entity.SubjectJpa;

/**
 * <p>Subject Mapper between Core Entity and JPA Entity. 
 * 
 * <p>Used to convert a Core Entity into a JPA Entity and vice versa.
 * 
 * @author Lucas Vasconcelos
 *
 */
@Component
public class SubjectMapper {
  
  /**
   * <p>Convert a Subject Jpa into a Subject Core Entity.
   * 
   * @param subjectJpa The SubjectJpa which will be converted.
   * @return The Subject Core Entity.
   */
  public Subject mapToCore(SubjectJpa subjectJpa) {
    return new Subject(subjectJpa.getId(), subjectJpa.getDescription());
  }

  /**
   * <p>Convert a Subject Jpa into a Subject Core Entity.
   * <p><b>IMPORTANT:</b> The id is not copied from the Core Entity to the JPA Entity.
   * 
   * @param subjectJpa The SubjectJpa which will be converted.
   * @return The Subject Core Entity.
   */
  public SubjectJpa mapToJpa(Subject subject) {
    return new SubjectJpa(subject.getDescription());
  }
  
}
