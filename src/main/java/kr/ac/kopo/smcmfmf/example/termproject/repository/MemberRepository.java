package kr.ac.kopo.smcmfmf.example.termproject.repository;

import kr.ac.kopo.smcmfmf.example.termproject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 로그인 시 아이디로 회원 조회
    Optional<Member> findByUsername(String username);
}