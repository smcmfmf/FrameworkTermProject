package kr.ac.kopo.smcmfmf.example.termproject.config;

import kr.ac.kopo.smcmfmf.example.termproject.domain.Car;
import kr.ac.kopo.smcmfmf.example.termproject.domain.Member;
import kr.ac.kopo.smcmfmf.example.termproject.repository.CarRepository;
import kr.ac.kopo.smcmfmf.example.termproject.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInit {

    @Bean
    public CommandLineRunner initData(MemberRepository memberRepository, CarRepository carRepository) {
        return args -> {
            if (carRepository.count() == 0) {
                String[] models = {"BMW iX", "BMW i4 M450", "BMW 5 Series", "BMW 7 Series"};
                String[] colors = {"white", "black", "silver", "blue", "red"};

                for (String model : models) {
                    for (String color : colors) {
                        carRepository.save(new Car(null, model, color));
                    }
                }
            }

            // [수정] 이메일 데이터 추가 ("user@test.com", "admin@test.com")
            if (memberRepository.findByUsername("user").isEmpty()) {
                memberRepository.save(new Member(null, "user", "user@test.com", "1234", "서울시 강남구", "010-1111-2222", "ROLE_USER"));
            }
            if (memberRepository.findByUsername("admin").isEmpty()) {
                memberRepository.save(new Member(null, "admin", "admin@test.com", "1234", "본사 관리팀", "010-9999-8888", "ROLE_ADMIN"));
            }
        };
    }
}