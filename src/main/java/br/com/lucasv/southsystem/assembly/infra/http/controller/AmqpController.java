package br.com.lucasv.southsystem.assembly.infra.http.controller;

import javax.validation.constraints.Positive;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasv.southsystem.assembly.core.entity.Session;
import br.com.lucasv.southsystem.assembly.core.entity.VotingResult;
import br.com.lucasv.southsystem.assembly.core.usecase.CalculateVotingResult;
import br.com.lucasv.southsystem.assembly.infra.http.dto.VotingResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/amqp")
@Tag(name = "RabbitMQ AMQP Broker", description = "Test the messaging with RabbitMQ")
public class AmqpController {

  @Value("${amqp.assembly.exchange-name}")
  private String exchangeName;

  @Value("${amqp.assembly.queue-name}")
  private String queueName;

  @Value("${amqp.assembly.routing-key-voting-result}")
  private String routingKeyVotingResult;

  private final RabbitTemplate rabbitTemplate;
  private final CalculateVotingResult calculateVotingResult;

  public AmqpController(
      CalculateVotingResult calculateVotingResult,
      RabbitTemplate rabbitTemplate) {

    this.calculateVotingResult = calculateVotingResult;
    this.rabbitTemplate = rabbitTemplate;
  }

  /**
   * Retrieve the voting result of a session and publish a message to AMQP
   * RabbitMQ.
   * 
   * @param sessionId The id of the {@link Session}.
   * @return A {@link VotingResultDto} with information about the voting result of
   *         the session.
   */
  @GetMapping("/sessions/{sessionId}/result")
  @Operation(summary = "Get the result of a voting session")
  public ResponseEntity<VotingResultDto> getVotingResultToAmqp(
      @Positive @PathVariable int sessionId) {
    
    VotingResult votingResult = calculateVotingResult.execute(sessionId);
    VotingResultDto votingResultDto = new VotingResultDto(
        votingResult.getYesVotesAmount(),
        votingResult.getNoVotesAmount());
    rabbitTemplate.convertAndSend(exchangeName, routingKeyVotingResult, votingResultDto);

    return ResponseEntity.ok(votingResultDto);
  }

}
