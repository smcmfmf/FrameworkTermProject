package kr.ac.kopo.smcmfmf.example.termproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model; // 차종 (예: BMW iX)
    private String color; // 색상 (예: white)
}