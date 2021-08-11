package br.com.lucasv.southsystem.assembly.infra.persistence.jpa.mapper;

import org.springframework.stereotype.Component;

import br.com.lucasv.southsystem.assembly.core.entity.Session;
import br.com.lucasv.southsystem.assembly.core.entity.Subject;
import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.entity.SessionJpa;
import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.entity.SubjectJpa;

/**
 * <p>Session Mapper between Core Entity and JPA Entity. 
 * 
 * <p>Used to convert a Core Entity into a JPA Entity and vice versa.
 * 
 * @author Lucas Vasconcelos
 *
 */
@Component
public class SessionMapper {

  private final SubjectMapper subjectMapper;

  public SessionMapper(SubjectMapper subjectMapper) {
    this.subjectMapper = subjectMapper;
  }
  
  /**
   * <p>Convert a Session Jpa into a Session Core Entity.
   * 
   * @param sessionJpa The SessionJpa which will be converted.
   * @return The Session Core Entity.
   */
  public Session mapToCore(SessionJpa sessionJpa) {
    Subject subject = subjectMapper.mapToCore(sessionJpa.getSubject());
    return new Session(
        sessionJpa.getId(),
        subject,
        sessionJpa.getStartDateTime(),
        sessionJpa.getEndDateTime());
  }
  
  /**
   * <p>Convert a Session Jpa into a Session Core Entity.
   * <p><b>IMPORTANT:</b> The id is not copied from the Core Entity to the JPA Entity.
   * 
   * @param sessionJpa The SessionJpa which will be converted.
   * @return The Session Core Entity.
   */
  public SessionJpa mapToJpa(Session session) {
    SubjectJpa subjectJpa = subjectMapper.mapToJpa(session.getSubject());
    subjectJpa.setId(session.getSubject().getId());
    return new SessionJpa(
        subjectJpa,
        session.getStartDateTime(),
        session.getEndDateTime());
  }
  
}
