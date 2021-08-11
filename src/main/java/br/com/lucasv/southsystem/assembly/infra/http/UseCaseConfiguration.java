package br.com.lucasv.southsystem.assembly.infra.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.lucasv.southsystem.assembly.adapter.persistence.MemberPersistenceAdapter;
import br.com.lucasv.southsystem.assembly.adapter.persistence.SessionPersistenceAdapter;
import br.com.lucasv.southsystem.assembly.adapter.persistence.SubjectPersistenceAdapter;
import br.com.lucasv.southsystem.assembly.adapter.persistence.VotePersistenceAdapter;
import br.com.lucasv.southsystem.assembly.core.usecase.CalculateVotingResult;
import br.com.lucasv.southsystem.assembly.core.usecase.ComputeVote;
import br.com.lucasv.southsystem.assembly.core.usecase.CreateSubject;
import br.com.lucasv.southsystem.assembly.core.usecase.StartVotingSession;

/**
 * Configure the beans for all use cases used by the Rest Controllers
 * 
 * @author Lucas Vasconcelos
 *
 */
@Configuration
public class UseCaseConfiguration {
  
  @Autowired
  private SubjectPersistenceAdapter subjectPersistenceAdapter;
  
  @Autowired
  private SessionPersistenceAdapter sessionPersistenceAdapter; 
  
  @Autowired
  private VotePersistenceAdapter votePersistenceAdapter;
  
  @Autowired
  private MemberPersistenceAdapter memberPersistenceAdapter;

  /**
   * Register a bean for CreateSubject use case
   * @return The CreateSubject use case
   */
  @Bean
  public CreateSubject getCreateSubjectInstance() {
    return new CreateSubject(subjectPersistenceAdapter);
  }
  
  /**
   * Register a bean for StartVotingSession use case
   * @return The StartVotingSession use case
   */
  @Bean
  public StartVotingSession getStartVotingSessionInstance() {
    return new StartVotingSession(subjectPersistenceAdapter, sessionPersistenceAdapter);
  }
  
  /**
   * Register a bean for ComputeVote use case
   * @return The ComputeVote use case
   */
  @Bean
  public ComputeVote getComputeVoteInstance() {
    return new ComputeVote(
        sessionPersistenceAdapter,
        memberPersistenceAdapter,
        votePersistenceAdapter);
  }
  
  /**
   * Register a bean for CalculateVotingResult use case
   * @return The CalculateVotingResult use case
   */
  @Bean
  public CalculateVotingResult getCalculateVotingResultInstance() {
    return new CalculateVotingResult(votePersistenceAdapter, sessionPersistenceAdapter);
  }
  
}
