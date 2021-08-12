package br.com.lucasv.southsystem.assembly.infra.http.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>The DTO with information about a Subject.
 * 
 * <p>This is design to exchange data through HTTP Controllers.
 * 
 * <p>More information about the meaning of the fields can be found in:
 * {@link br.com.lucasv.southsystem.assembly.core.entity.Subject}
 * 
 * @author Lucas Vasconcelos
 * @see br.com.lucasv.southsystem.assembly.core.entity.Subject
 */
public class SubjectDto {

  @JsonProperty(access=JsonProperty.Access.READ_ONLY)
  @Schema(accessMode=Schema.AccessMode.READ_ONLY)
  private int id;

  @NotBlank
  private String description;

  /**
   * <p>Create a SubjectDto with the specified parameters.
   *  
   * @param id The subject id.
   * @param description The description of the subject.
   */
  public SubjectDto(int id, String description) {
    this.id = id;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
