package br.com.lucasv.southsystem.assembly.infra.http.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasv.southsystem.assembly.core.usecase.ComputeVote;
import br.com.lucasv.southsystem.assembly.infra.http.dto.VoteDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Rest Controller perform operations on a {@link Vote} .
 * 
 * @author Lucas Vasconcelos
 *
 */
@RestController
@RequestMapping("/subjects/{subjectId}/sessions/{sessionId}/votes")
@Tag(name="Vote", description="Operations for Vote management")
public class VoteController {

  private final ComputeVote computeVote;
  
  public VoteController(ComputeVote computeVote) {
    this.computeVote = computeVote;
  }

  /**
   * <p>Register a member's vote in a session.
   * 
   * @param subjectDto The {@link VoteDto} with the info to register the vote.
   * @return A {@link VoteDto} with information about the registered vote.
   */
  @PostMapping
  @Operation(summary="Vote for a session. It can be either yes or no")
  public ResponseEntity<VoteDto> computeVote(
      @PathVariable int sessionId,
      @Valid @RequestBody VoteDto vote) {
    
    computeVote.execute(sessionId, vote.getMemberId(), vote.getChoice());
    
    return ResponseEntity.ok().build();
  }
  
}
