package br.com.lucasv.southsystem.assembly.infra.http.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasv.southsystem.assembly.infra.http.dto.MemberVotingStatusDto;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * <p>Rest to retrieve informations about the Member
 * 
 * Rest Controller only designed to meet the requirements of
 * Bonus 1 of the Challenge
 * 
 * @author Lucas Vasconcelos
 *
 */
@RestController
@RequestMapping("/members")
@Tag(name="Member", description="Operations for member management")
public class MemberController {
  
  private final MemberVotingStatusProxy memberVotingStatusProxy;
  
  public MemberController(MemberVotingStatusProxy memberVotingStatusProxy) {
    super();
    this.memberVotingStatusProxy = memberVotingStatusProxy;
  }

  /**
   * Return if the member is able to vote in the specified session.
   * 
   * @param cpf The cpf of them member.
   * @return The status about if the member is able to vote in the session.
   *        Possible status comes from external API and it can be:
   *        ABLE_TO_VOTE, UNABLE_TO_VOTE
   */
  @GetMapping("/{cpf}/member-status")
  @Operation(summary="Get the voting status of a member")
  @Retry(name="assembly")
  public ResponseEntity<MemberVotingStatusDto> getMemberVotingStatus(@PathVariable String cpf) {
    MemberVotingStatusDto memberVotingStatusDto = memberVotingStatusProxy.getMemberStatusVote(cpf);
        
    return ResponseEntity.ok(memberVotingStatusDto);
  }
  
}
