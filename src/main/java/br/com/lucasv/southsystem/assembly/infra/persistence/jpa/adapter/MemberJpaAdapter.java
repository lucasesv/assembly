package br.com.lucasv.southsystem.assembly.infra.persistence.jpa.adapter;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;

import br.com.lucasv.southsystem.assembly.adapter.persistence.MemberPersistenceAdapter;
import br.com.lucasv.southsystem.assembly.core.entity.Member;
import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.entity.MemberJpa;
import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.mapper.MemberMapper;
import br.com.lucasv.southsystem.assembly.infra.persistence.jpa.repository.MemberRepository;

@Component
public class MemberJpaAdapter implements MemberPersistenceAdapter {

  private final MemberRepository memberRepository;
  private final MemberMapper memberMapper;

  public MemberJpaAdapter(MemberRepository memberRepository, MemberMapper memberMapper) {
    this.memberRepository = memberRepository;
    this.memberMapper = memberMapper;
  }

  @Override
  public int saveMember(Member member) {
    MemberJpa memberJpa = memberMapper.mapToJpa(member);
    memberRepository.save(memberJpa);
    member.setId(memberJpa.getId());
    return member.getId();
  }

  @Override
  public Member getMember(int memberId) {
    MemberJpa memberJpa = memberRepository
        .findById(memberId)
        .orElseThrow(() -> new NoSuchElementException("Member with " + memberId + " not found."));
    Member member = memberMapper.mapToCore(memberJpa);
    return member;
  }
  
}
