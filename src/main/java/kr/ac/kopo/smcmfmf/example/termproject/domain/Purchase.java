package kr.ac.kopo.smcmfmf.example.termproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"member", "car"}) // 순환 참조 방지
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 주문 번호

    // Member와 다대일 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // Car와 다대일 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    private String usagePurpose; // 이용 목적
    private String deliveryDate; // 인도 날짜

    // 배송 정보 (회원 정보와 다를 수 있으므로 별도 저장 요청하신 대로 추가)
    private String address;      // 배송 주소
    private String phone;        // 배송 연락처
}