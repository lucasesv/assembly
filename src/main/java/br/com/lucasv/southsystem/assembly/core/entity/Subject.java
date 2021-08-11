package br.com.lucasv.southsystem.assembly.core.entity;

/**
 * <p>A Subject that can be voted in assembly sessions.
 * 
 * @author Lucas Vasconcelos
 *
 */
public class Subject {

  private int id;
  private String description;
  private Session session;

  /**
   * <p>Create a Subject with a specific id and description
   * 
   * @param id The Subject id
   * @param description The Subject description
   */
  public Subject(int id, String description) {
    initSubject(id, description);
  }

  /**
   * <p>Create a Subject with a specific id and description.
   * Id is set to 0.
   * 
   * @param description The Subject description
   */
  public Subject(String description) {
    initSubject(0, description);
  }

  private void initSubject(int id, String description) {
    if (!isValidId(id) || !isValidDescription(description))
      throw new IllegalArgumentException();

    this.id = id;
    this.description = description;
  }

  private boolean isValidId(int id) {
    return id >= 0;
  }

  private boolean isValidDescription(String description) {
    return description != null && !description.isBlank();
  }

  /**
   * Get the subject id.
   * 
   * @return The subject id.
   */
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  /**
   * Get the subject description.
   * 
   * @return The subject description.
   */
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Get the current session.
   * 
   * @return The current session.
   */
  public Session getSession() {
    return session;
  }

  public void setSession(Session session) {
    this.session = session;
  }

  /**
   * Subject equality is based on it's id
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Subject other = (Subject) obj;
    if (id != other.id)
      return false;
    if (this == obj)
      return true;
    return true;
  }

}
