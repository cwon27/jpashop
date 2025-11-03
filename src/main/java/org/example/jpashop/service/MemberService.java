package org.example.jpashop.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.jpashop.domain.Member;
import org.example.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 읽기 전용 트랜잭션 기본으로 걸어두기
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	// 회원 가입
	@Transactional
	public Long join(Member member) {
		//중복 회원 검증
		validateDuplicateMember(member);
		
		// 저장
		memberRepository.save(member);
		
		return member.getId();
	}
	
	private void validateDuplicateMember(Member member) {
		// 중복이면 예외 발생
		List<Member> findMembers =  memberRepository.findByName(member.getName());
		if (!findMembers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}

	// 회원 전체 조회
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	// 회원 1명 조회
	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}
}
