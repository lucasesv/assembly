package br.com.lucasv.southsystem.assembly.infra.http.exception;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * A nice Response to send the user when an error occurs. 
 * 
 * @author Lucas Vasconcelos
 *
 */
@JsonInclude(Include.NON_NULL)
public class ExceptionResponse {

  private LocalDateTime timestamp;
  private Map<String, String> errors;
  private String details;
  
  /**
   * Create a ExceptionResponse with 1 error only.
   * 
   * @param timestamp The timestamp when the error occurred.
   * @param errors The error message that should be in the response.
   * @param details Extra details about the error.
   */
  public ExceptionResponse(LocalDateTime timestamp, String msg, String details) {
    this.timestamp = timestamp;
    this.details = details;
    this.errors = Map.of("error", msg);
  }
  
  /**
   * Create a ExceptionResponse with more than 1 errors.
   * 
   * @param timestamp The timestamp when the error occurred.
   * @param errors A map with all errors that should be in the response.
   * @param details Extra details about the error.
   */
  public ExceptionResponse(LocalDateTime timestamp, Map<String, String> errors, String details) {
    this.timestamp = timestamp;
    this.details = details;
    this.errors = errors;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public String getDetails() {
    return details;
  }

  public Map<String, String> getErrors() {
    return errors;
  }

  public void setErrors(Map<String, String> errors) {
    this.errors = errors;
  }
  
}
