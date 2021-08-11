package br.com.lucasv.southsystem.assembly.infra.http.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.lucasv.southsystem.assembly.infra.http.dto.MemberVotingStatusDto;

/**
 * <p>Feign proxy to make requests to https://user-info.herokuapp.com/users/{cpf}.
 * The target endpoint returns status the about if the member is able to vote in
 * the session. Possible status are: ABLE_TO_VOTE, UNABLE_TO_VOTE. 
 * 
 * <p>This interface was only designed to meet the requirements of
 * the Bonus 1 of the challenge. It is not part of the application
 * itself. 
 *
 */
@FeignClient(name="member-voting-status", url="https://user-info.herokuapp.com")
public interface MemberVotingStatusProxy {

  /**
   * Make a request to https://user-info.herokuapp.com/users/{cpf} to get
   * the status the about if the member is able to vote and then return
   * this status.
   * @param cpf The cpf of them member.
   * @return Status the about if the member is able to vote in
   *        the session. Possible status are: ABLE_TO_VOTE, UNABLE_TO_VOTE.
   */
  @GetMapping("/users/{cpf}")
  MemberVotingStatusDto getMemberStatusVote(@PathVariable String cpf);
  
}
