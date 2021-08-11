package br.com.lucasv.southsystem.assembly.core.entity;

/**
 * <p>A Member of an assembly.
 * 
 *  Only Members of an assembly can vote for subjects.
 * 
 * @author Lucas Vasconcelos
 *
 */
public class Member {

  private int id;

  /**
   * Create a Member with a specific id
   * @param id
   */
  public Member(int id) {
    this.id = id;
  }

  /**
   * @return the Member's id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id the id to set to the Member
   */
  public void setId(int id) {
    this.id = id;
  }



  /**
   * Member equality is based on it's id
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Member other = (Member) obj;
    if (id != other.id)
      return false;
    if (this == obj)
      return true;
    return true;
  }

}
