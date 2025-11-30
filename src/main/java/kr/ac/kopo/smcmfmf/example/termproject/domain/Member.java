package kr.ac.kopo.smcmfmf.example.termproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId; // 고객 아이디

    @Column(nullable = false)
    private String userName; // 고객 성명

    @Column(nullable = false)
    private String userPassword; // 비밀번호

    private String userEmail; // 이메일
    private String userAddress; // 주소
    private String userPhone; // 전화번호
    private String role; // 권한 (ROLE_USER, ROLE_ADMIN)
}