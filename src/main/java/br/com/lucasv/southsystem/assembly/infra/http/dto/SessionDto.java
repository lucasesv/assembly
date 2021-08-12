package br.com.lucasv.southsystem.assembly.infra.http.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>The DTO with information about a Session.
 * 
 * <p>This is design to exchange data through HTTP Controllers.
 * 
 * <p>More information about the meaning of the fields can be found in:
 * {@link br.com.lucasv.southsystem.assembly.core.entity.Session}
 * 
 * @author Lucas Vasconcelos
 * @see br.com.lucasv.southsystem.assembly.core.entity.Session
 */
@JsonInclude(Include.NON_NULL)
public class SessionDto {
  
  @JsonProperty(access=JsonProperty.Access.READ_ONLY)
  @Schema(accessMode=Schema.AccessMode.READ_ONLY)
  private int id;
  
  @Positive
  private int duration;

  @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime startDateTime;
  
  @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime endDateTime;
  
  /**
   * <p>Create a SessionDto with the specified parameters.
   *  
   * @param id The session id.
   * @param duration The duration of the session.
   * @param startDateTime The start date and time of the session.
   * @param endDateTime The end date and time of the session.
   */
  public SessionDto(int id, int duration, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    this.id = id;
    this.duration = duration;
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public LocalDateTime getStartDateTime() {
    return startDateTime;
  }

  public void setStartDateTime(LocalDateTime startDateTime) {
    this.startDateTime = startDateTime;
  }

  public LocalDateTime getEndDateTime() {
    return endDateTime;
  }

  public void setEndDateTime(LocalDateTime endDateTime) {
    this.endDateTime = endDateTime;
  }

  public int getDuration() {
    return duration;
  }
  
  public void setDuration(int duration) {
    this.duration = duration;
  }
  
}
