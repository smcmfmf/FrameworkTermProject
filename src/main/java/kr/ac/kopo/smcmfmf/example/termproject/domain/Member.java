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
    private String username; // 아이디

    @Column(nullable = false)
    private String password; // 비밀번호

    private String address;  // 주소
    private String phone;    // 전화번호
    private String role;     // 권한 (ROLE_USER, ROLE_ADMIN)
}