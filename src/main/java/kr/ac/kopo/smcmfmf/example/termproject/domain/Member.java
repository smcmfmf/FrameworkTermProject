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
    private String userId; // 아이디 (중복 불가)
    private String userEmail; // 이메일 (중복 불가)
    private String userPhone; // 전화번호 (중복 불가)

    @Column(nullable = false)
    private String userName; // 이름
    private String userPassword; // 비밀번호
    private String userAddress; // 주소
    private String role; // 권한 (ROLE_USER, ROLE_ADMIN)
}