package br.com.lucasv.southsystem.assembly.infra.http.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.lucasv.southsystem.assembly.core.entity.Subject;
import br.com.lucasv.southsystem.assembly.core.usecase.CreateSubject;
import br.com.lucasv.southsystem.assembly.infra.http.dto.SubjectDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Rest Controller perform operations on a {@link Subject} .
 * 
 * @author Lucas Vasconcelos
 *
 */
@RestController
@RequestMapping("/subjects")
@Tag(name="Subject", description="Operations for Subject management")
public class SubjectController {
  
  private final CreateSubject createSubject;

  public SubjectController(CreateSubject createSubject) {
    this.createSubject = createSubject;
  }

  /**
   * Create a subject.
   * 
   * @param subjectDto The {@link SubjectDto} with the info to create a subject.
   * @return A SubjectDto with information about the subject that has been created.
   */
  @PostMapping
  @Operation(summary="Create a new subject")
  public ResponseEntity<SubjectDto> createSubject(@Valid @RequestBody SubjectDto subject) {
    
    Subject createdSubject = createSubject.execute(subject.getDescription());
    SubjectDto createdSubjectDto =
        new SubjectDto(createdSubject.getId(), createdSubject.getDescription());
    
    URI uri = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(createdSubjectDto.getId())
        .toUri();
        return ResponseEntity.created(uri).body(createdSubjectDto);
  }
}
