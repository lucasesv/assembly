package br.com.lucasv.southsystem.assembly.infra.persistence.jpa.adapter;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;

import br.com.lucasv.southsystem.assembly.adapter.persistence.SubjectPersistenceAdapter;
import br.com.lucasv.southsystem.assembly.core.entity.Subject;
import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.entity.SubjectJpa;
import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.mapper.SubjectMapper;
import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.repository.SubjectRepository;

@Component
public class SubjectJpaAdapter implements SubjectPersistenceAdapter {

  private final SubjectRepository subjectRepository;
  private final SubjectMapper subjectMapper;

  public SubjectJpaAdapter(SubjectRepository subjectRepository, SubjectMapper subjectMapper) {
    this.subjectRepository = subjectRepository;
    this.subjectMapper = subjectMapper;
  }

  @Override
  public int saveSubject(Subject subject) {
    SubjectJpa subjectJpa = subjectMapper.mapToJpa(subject);
    subjectRepository.save(subjectJpa);
    subject.setId(subjectJpa.getId());
    return subject.getId();
  }

  @Override
  public Subject getSubject(int id) {
    SubjectJpa subjectJpa = subjectRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("Subject with " + id + " not found."));
    Subject subject = subjectMapper.mapToCore(subjectJpa);
    return subject;
  }
  
}
