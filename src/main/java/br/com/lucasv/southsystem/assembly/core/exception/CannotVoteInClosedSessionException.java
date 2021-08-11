package br.com.lucasv.southsystem.assembly.core.exception;

public class CannotVoteInClosedSessionException extends RuntimeException {

  private static final long serialVersionUID = -1783677800859781233L;

  public CannotVoteInClosedSessionException(int sessionId) {
    super("Session with " + sessionId + " is closed. Cannot vote in closed session.");
  }
}
