package br.com.lucasv.southsystem.assembly.infra.http.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.lucasv.southsystem.assembly.core.entity.Session;
import br.com.lucasv.southsystem.assembly.core.entity.Subject;
import br.com.lucasv.southsystem.assembly.core.entity.VotingResult;
import br.com.lucasv.southsystem.assembly.core.usecase.CalculateVotingResult;
import br.com.lucasv.southsystem.assembly.core.usecase.StartVotingSession;
import br.com.lucasv.southsystem.assembly.infra.http.dto.SessionDto;
import br.com.lucasv.southsystem.assembly.infra.http.dto.VotingResultDto;
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

  @Value("${amqp.assembly.exchange-name}")
  private String exchangeName;
  
  @Value("${amqp.assembly.queue-name}")
  private String queueName;
  
  @Value("${amqp.assembly.routing-key-voting-result}")
  private String routingKeyVotingResult;
  
  private final StartVotingSession startVotingSession;
  private final CalculateVotingResult calculateVotingResult;
  private final RabbitTemplate rabbitTemplate;

  public SessionController(StartVotingSession startVotingSession,
      CalculateVotingResult calculateVotingResult, RabbitTemplate rabbitTemplate) {
    this.startVotingSession = startVotingSession;
    this.calculateVotingResult = calculateVotingResult;
    this.rabbitTemplate = rabbitTemplate;
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

  /**
   * Retrieve the voting result of a session.
   * 
   * @param sessionId The id of the {@link Session}.
   * @return A {@link VotingResultDto} with information about the voting result of
   *         the session.
   */
  @GetMapping("/{sessionId}/result")
  @Operation(summary = "Get the result of a voting session")
  public ResponseEntity<VotingResultDto> getVotingResult(@Positive @PathVariable int sessionId) {
    VotingResult votingResult = calculateVotingResult.execute(sessionId);

    VotingResultDto votingResultDto = new VotingResultDto(
        votingResult.getYesVotesAmount(),
        votingResult.getNoVotesAmount());

    return ResponseEntity.ok(votingResultDto);
  }

  /**
   * Retrieve the voting result of a session and publish a message to AMQP
   * RabbitMQ.
   * 
   * @param sessionId The id of the {@link Session}.
   * @return A {@link VotingResultDto} with information about the voting result of
   *         the session.
   */
  @GetMapping("/{sessionId}/result-amqp")
  @Operation(summary = "Get the result of a voting session")
  public ResponseEntity<VotingResultDto> getVotingResultToAmqp(@Positive @PathVariable int sessionId) {
    VotingResult votingResult = calculateVotingResult.execute(sessionId);

    VotingResultDto votingResultDto = new VotingResultDto(
        votingResult.getYesVotesAmount(),
        votingResult.getNoVotesAmount());
    
    rabbitTemplate.convertAndSend(exchangeName, routingKeyVotingResult, votingResultDto);

    return ResponseEntity.ok(votingResultDto);
  }
  
}
