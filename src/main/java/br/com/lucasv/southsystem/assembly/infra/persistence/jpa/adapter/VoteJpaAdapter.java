package br.com.lucasv.southsystem.assembly.infra.persistence.jpa.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.lucasv.southsystem.assembly.adapter.persistence.VotePersistenceAdapter;
import br.com.lucasv.southsystem.assembly.core.entity.Vote;
import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.entity.VoteJpa;
import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.mapper.VoteMapper;
import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.repository.VoteRepository;

@Component
public class VoteJpaAdapter implements VotePersistenceAdapter {
  
  private final VoteMapper voteMapper;
  private final VoteRepository voteRepository;

  public VoteJpaAdapter(VoteMapper voteMapper, VoteRepository voteRepository) {
    this.voteMapper = voteMapper;
    this.voteRepository = voteRepository;
  }

  @Override
  public int saveVote(Vote vote) {
    VoteJpa voteJpa = voteMapper.mapToJpa(vote);
    voteRepository.save(voteJpa);
    vote = voteMapper.mapToCore(voteJpa);
    return vote.getId();
  }

  @Override
  public List<Vote> getVotes(int sessionId) {
    List<VoteJpa> votesJpa = voteRepository.findBySessionId(sessionId);
    return votesJpa.stream()
        .map((v) -> voteMapper.mapToCore(v))
        .collect(Collectors.toList());
  }

}
