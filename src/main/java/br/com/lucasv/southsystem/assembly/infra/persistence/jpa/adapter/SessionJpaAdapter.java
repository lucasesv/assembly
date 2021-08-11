package br.com.lucasv.southsystem.assembly.infra.persistence.jpa.adapter;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;

import br.com.lucasv.southsystem.assembly.adapter.persistence.SessionPersistenceAdapter;
import br.com.lucasv.southsystem.assembly.core.entity.Session;
import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.entity.SessionJpa;
import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.mapper.SessionMapper;
import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.repository.SessionRepository;

@Component
public class SessionJpaAdapter implements SessionPersistenceAdapter {
  
  private final SessionRepository sessionRepository;
  private final SessionMapper sessionMapper;

  public SessionJpaAdapter(SessionRepository sessionRepository, SessionMapper sessionMapper) {
    this.sessionRepository = sessionRepository;
    this.sessionMapper = sessionMapper;
  }

  @Override
  public int saveSession(Session session) {
    SessionJpa sessionJpa = sessionMapper.mapToJpa(session);
    sessionRepository.save(sessionJpa);
    session.setId(sessionJpa.getId());
    return session.getId();
  }

  @Override
  public Session getSession(int sessionId) {
    SessionJpa sessionJpa = sessionRepository
        .findById(sessionId)
        .orElseThrow(() -> new NoSuchElementException("Session " + sessionId + " not found."));
    Session session = sessionMapper.mapToCore(sessionJpa);
    return session;
  }

}
