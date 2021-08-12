package br.com.lucasv.southsystem.assembly.infra.persistence.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * <p>JPA entity to store a Member.
 * 
 * <p>Because the Core Entities of the application is isolated
 * from the persistence layer, it is necessary to crate
 * JPA entities wrappers to store the data of the Core Entities.
 * 
 * <p>The information about the fields can be found in
 * {@link br.com.lucasv.southsystem.assembly.core.entity.Member}.
 * 
 * @author Lucas Vasconcelos 
 * @see br.com.lucasv.southsystem.assembly.core.entity.Member
 *
 */
@Entity(name="Member")
public class MemberJpa {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  public MemberJpa() {
  }
  
  public MemberJpa(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
  
}
