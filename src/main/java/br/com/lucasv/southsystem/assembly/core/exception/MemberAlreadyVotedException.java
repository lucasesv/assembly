package br.com.lucasv.southsystem.assembly.core.exception;

public class MemberAlreadyVotedException extends RuntimeException {
  
  private static final long serialVersionUID = 8065676394764296338L;

  public MemberAlreadyVotedException(int memberId) {
    super("Member with Id " + memberId + " has already voted.");
  }
}
