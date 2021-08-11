package br.com.lucasv.southsystem.assembly.infra.http.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.lucasv.southsystem.assembly.core.entity.Session;
import br.com.lucasv.southsystem.assembly.core.entity.Subject;
import br.com.lucasv.southsystem.assembly.core.usecase.StartVotingSession;
import br.com.lucasv.southsystem.assembly.infra.http.dto.SessionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Rest Controller perform operations on a {@link Session}.
 * 
 * @author Lucas Vasconcelos
 *
 */
@RestController
@RequestMapping("/subjects/{subjectId}/sessions")
@Tag(name="Session", description="Operations for Session management")
public class SessionController {

  private final StartVotingSession startVotingSession;

  public SessionController(StartVotingSession startVotingSession) {

    this.startVotingSession = startVotingSession;
  }

  /**
   * Start a voting session for a subject.
   * 
   * @param subjectId  The id of the {@link Subject} which the session is bounded.
   * @param sessionDto The {@link SessionDto} with the info to start a session.
   * @return A SessionDto with information about the session that has been
   *         started.
   */
  @PostMapping
  @Operation(summary = "Start a voting session for a subject")
  public ResponseEntity<SessionDto> startVotingSession(
      @PathVariable int subjectId,
      @Valid @RequestBody SessionDto sessionDto) {

    Session createdSession = startVotingSession.execute(subjectId, sessionDto.getDuration());
    SessionDto createdSessionDto = new SessionDto(
        createdSession.getId(),
        sessionDto.getDuration(),
        createdSession.getStartDateTime(),
        createdSession.getEndDateTime());

    URI uri = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(createdSessionDto.getId())
        .toUri();

    return ResponseEntity.created(uri).body(createdSessionDto);
  }

}
