package br.com.lucasv.southsystem.assembly.core.usecase;

import br.com.lucasv.southsystem.assembly.adapter.persistence.SessionPersistenceAdapter;
import br.com.lucasv.southsystem.assembly.adapter.persistence.SubjectPersistenceAdapter;
import br.com.lucasv.southsystem.assembly.core.entity.Session;
import br.com.lucasv.southsystem.assembly.core.entity.Subject;

/**
 * <p>Use Case for starting a voting session.
 * 
 * 
 * @author Lucas Vasconcelos
 *
 */
public class StartVotingSession {

  private final SubjectPersistenceAdapter subjectPersistenceAdapter;
  private final SessionPersistenceAdapter sessionPersistenceAdapter;

  /**
   * <p>Create use case for starting a voting session.
   * 
   * @param subjectPersistenceAdapter An adapter to manipulate
   *        stored information about subjects.
   * @param sessionPersistenceAdapter An adapter to manipulate
   *        stored information about sessions.
   */
  public StartVotingSession(
      SubjectPersistenceAdapter subjectPersistenceAdapter,
      SessionPersistenceAdapter sessionPersistenceAdapter) {
    
    this.subjectPersistenceAdapter = subjectPersistenceAdapter;
    this.sessionPersistenceAdapter = sessionPersistenceAdapter;
  }

  /**
   * <p>Start a voting session for a subject.
   * <p>The session is started with {@code DEFAULT_DURATION} of a {@link Session}.
   * 
   * @param subjectId The id of the {@link Subject} which the session is bounded.
   *         Must be greater than 0.
   * @return The session that has been started.
   * @throws IllegalArgumentException Thrown when the parameters are invalid.
   */
  public Session execute(int subjectId) throws IllegalArgumentException {
    return createSession(subjectId, Session.DEFAULT_DURATION);
  }

  /**
   * <p>Start a voting session.
   * <p>The session is started with {@code DEFAULT_DURATION} of a {@link Session}.
   * 
   * @param subjectId The id of the {@link Subject} which the session is bounded.
   *         Must be greater than 0.
   * @param duration The duration in minutes of the session. Must be greater the 0.
   * @return The session that has been started.
   * @throws IllegalArgumentException Thrown when the parameters are invalid.
   */
  public Session execute(int subjectId, int sessionDuration) throws IllegalArgumentException {
    return createSession(subjectId, sessionDuration);
  }

  /**
   * Handy method to centralize the creation of a session.
   * 
   * @param subjectId The id of the {@link Subject} which the session is bounded.
   *         Must be greater than 0.
   * @param sessionDuration The duration in minutes of the session. Must be greater the 0.
   * @return The session that has been started.
   * @throws IllegalArgumentException Thrown when the parameters are invalid.
   */
  private Session createSession(int subjectId, int sessionDuration)
      throws IllegalArgumentException {
    
    validateInputs(subjectId, sessionDuration);

    Subject subject = subjectPersistenceAdapter.getSubject(subjectId);
    Session session = new Session(subject, sessionDuration);
    int sessionId = sessionPersistenceAdapter.saveSession(session);
    session.setId(sessionId);
    subject.setSession(session);

    return session;
  }
  
  /**
   * Validate the inputs of the use case.
   * 
   * @param subjectId The id of the {@link Subject} which the session is bounded.
   *         Must be greater than 0.
   * @param sessionDuration The duration in minutes of the session. Must be greater the 0.
   */
  private void validateInputs(int subjectId, int sessionDuration) {
    validateSubjectId(subjectId);
    validateDuration(sessionDuration);
  }

  /**
   * Validate the subject id of the session.
   * 
   * @param subjectId The id of the {@link Subject} which the session is bounded.
   *         Must be greater than 0.
   */
  private void validateSubjectId(int subjectId) {
    if (subjectId <= 0)
      throw new IllegalArgumentException("Subject Id must be greater than 0.");
  }

  /**
   * Validate the duration of the session.
   * 
   * @param sessionDuration The duration in minutes of the session. Must be greater the 0.
   */
  private void validateDuration(int duration) {
    if (duration <= 0)
      throw new IllegalArgumentException("Session duration must be greater than 0.");
  }

}
