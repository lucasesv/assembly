package br.com.lucasv.southsystem.assembly.infra.persistence.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * <p>JPA entity to store a Subject.
 * 
 * <p>Because the Core Entities of the application is isolated
 * from the persistence layer, it is necessary to crate
 * JPA entities wrappers to store the data of the Core Entities.
 * 
 * <p>The information about the fields can be found in
 * {@link br.com.lucasv.southsystem.assembly.core.entity.Subject}.
 * 
 * @author Lucas Vasconcelos 
 * @see br.com.lucasv.southsystem.assembly.core.entity.Subject
 *
 */
@Entity(name="Subject")
public class SubjectJpa {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;
  
  private String description;
  
  public SubjectJpa() {
  }
  
  public SubjectJpa(Integer id, String description) {
    super();
    this.id = id;
    this.description = description;
  }

  public SubjectJpa(String description) {
    super();
    this.description = description;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
}
