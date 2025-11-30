package kr.ac.kopo.smcmfmf.example.termproject.config;

import kr.ac.kopo.smcmfmf.example.termproject.domain.Car;
import kr.ac.kopo.smcmfmf.example.termproject.domain.Member;
import kr.ac.kopo.smcmfmf.example.termproject.repository.CarRepository;
import kr.ac.kopo.smcmfmf.example.termproject.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInit {

    @Bean
    public CommandLineRunner initData(MemberRepository memberRepository,
                                      CarRepository carRepository,
                                      PasswordEncoder passwordEncoder) {
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

            if (memberRepository.findByUserId("user").isEmpty()) {
                memberRepository.save(new Member(
                        null,
                        "user",
                        "일반사용자",
                        passwordEncoder.encode("1234"),
                        "user@test.com",
                        "서울시 강남구",
                        "010-1111-2222",
                        "ROLE_USER"
                ));
            }

            if (memberRepository.findByUserId("admin").isEmpty()) {
                memberRepository.save(new Member(
                        null,
                        "admin",
                        "관리자",
                        passwordEncoder.encode("1234"),
                        "admin@test.com",
                        "본사 관리팀",
                        "010-9999-8888",
                        "ROLE_ADMIN"
                ));
            }
        };
    }
}