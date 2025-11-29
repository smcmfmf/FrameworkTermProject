package kr.ac.kopo.smcmfmf.example.termproject.repository;

import kr.ac.kopo.smcmfmf.example.termproject.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    // 모델명과 색상으로 차량 조회 (주문 처리를 위해)
    Optional<Car> findByModelAndColor(String model, String color);
}