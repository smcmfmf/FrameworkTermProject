# FrameworkTermProject

Spring Boot와 Spring Data JPA를 활용해 구현한 **BMW 차량 구매 웹 애플리케이션**입니다.

회원가입과 로그인부터 차량 선택, 구매 정보 저장, 관리자 주문 관리까지 구현하며 Spring Boot 기반 웹 애플리케이션의 기본 구조와 JPA를 활용한 데이터베이스 연동, 엔티티 간 연관관계, Spring Security 기반 인증·인가를 학습했습니다.

---

## 📌 Project Overview

### 프로젝트명

**FrameworkTermProject**

### 프로젝트 목적

* Spring Boot 기반 웹 애플리케이션 구현
* Spring Data JPA를 활용한 데이터베이스 연동
* 회원·차량·구매 데이터의 엔티티 설계
* JPA `@ManyToOne` 연관관계 및 지연 로딩 적용
* Spring Security 기반 로그인 및 권한 관리
* 사용자와 관리자 기능 분리
* Thymeleaf 기반 서버 사이드 웹 페이지 구현

---

## 🛠️ Tech Stack

### Backend

* Java 17
* Spring Boot 3.5.3
* Spring MVC
* Spring Data JPA
* Spring Security

### Frontend

* Thymeleaf
* HTML / CSS
* JavaScript

### Database

* MySQL

### Build

* Gradle
* Lombok

프로젝트의 `build.gradle`에서 Java 17과 Spring Boot 3.5.3을 기반으로 Spring Web, Thymeleaf, Spring Security, Spring Data JPA, MySQL Connector 등을 사용하고 있습니다.

---

## 🏗️ Project Structure

```text
src/main/java/kr/ac/kopo/smcmfmf/example/termproject

├── config
│   ├── DataInit.java
│   └── SecurityConfig.java
│
├── controller
│   └── AlphaController.java
│
├── domain
│   ├── Car.java
│   ├── Member.java
│   └── Purchase.java
│
├── repository
│   ├── CarRepository.java
│   ├── MemberRepository.java
│   └── PurchaseRepository.java
│
└── service
    └── CustomUserDetailsService.java
```

---

## 🔑 주요 기능

### 1. 회원가입

회원가입 과정에서 사용자 정보를 검증하고 DB에 저장합니다.

* 아이디 중복 확인
* 이메일 중복 확인
* 전화번호 중복 확인
* 비밀번호 BCrypt 암호화
* 기본 권한 `ROLE_USER` 부여

회원가입 시 `MemberRepository`의 `existsBy...` 메서드를 활용해 중복 여부를 확인하고, `PasswordEncoder`를 통해 비밀번호를 암호화하여 저장합니다.

---

### 2. Spring Security 기반 로그인

Spring Security의 `UserDetailsService`를 구현한 `CustomUserDetailsService`에서 DB의 회원 정보를 조회하여 인증에 사용합니다.

```text
로그인 요청
   ↓
Spring Security
   ↓
CustomUserDetailsService
   ↓
MemberRepository
   ↓
Member 조회
   ↓
UserDetails 생성
```

비밀번호는 `BCryptPasswordEncoder`를 사용해 암호화하고 있습니다.

---

### 3. 사용자 / 관리자 권한 분리

사용자의 권한을 `ROLE_USER`, `ROLE_ADMIN`으로 구분하고 URL에 따라 접근 권한을 설정했습니다.

```text
일반 사용자
 ├── 로그인
 ├── 차량 선택
 └── 차량 구매

관리자
 └── 주문 목록 조회 / 수정 / 삭제
```

Spring Security 설정에서 `/admin/**` 경로는 `ADMIN` 권한을 가진 사용자만 접근할 수 있도록 구성했습니다.

---

### 4. JPA 기반 엔티티 설계

프로젝트에서는 다음 세 가지 주요 엔티티를 사용합니다.

```text
Member
  │
  │ @ManyToOne
  ▼
Purchase
  ▲
  │ @ManyToOne
  │
Car
```

`Purchase` 엔티티에서 `Member`와 `Car`를 각각 `@ManyToOne`으로 연결하고 `@JoinColumn`을 통해 외래 키를 지정했습니다.

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "member_id")
private Member member;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "car_id")
private Car car;
```

또한 `FetchType.LAZY`를 적용해 연관된 엔티티를 필요한 시점에 조회하도록 구성했습니다.

---

### 5. 차량 구매 데이터 처리

사용자가 차량 모델과 색상을 선택하면 해당 차량 정보를 조회하고, 존재하지 않는 차량이라면 새 차량 정보를 저장합니다.

이후 로그인한 회원 정보와 차량 정보를 `Purchase` 엔티티에 연결하여 구매 정보를 저장합니다.

```text
사용자 로그인
     ↓
차량 모델 / 색상 선택
     ↓
Car 조회
     ↓
Member 조회
     ↓
Purchase 생성
     ↓
구매 정보 저장
```

---

### 6. 관리자 주문 관리

관리자 페이지에서는 구매 정보를 조회하고 다음 기능을 수행할 수 있습니다.

* 주문 목록 조회
* 주문 상세 수정
* 주문 삭제
* 차량 정보 수정

관리자 접근은 Spring Security를 통해 제한했습니다.

---

## 💡 구현 과정에서 학습한 내용

### JPA 연관관계와 지연 로딩

`Purchase` 엔티티와 `Member`, `Car`의 관계를 `@ManyToOne`으로 설계하고 `FetchType.LAZY`를 적용하면서 객체 간 관계와 데이터베이스의 외래 키 관계를 이해했습니다.

또한 Lombok의 `@ToString` 사용 시 연관된 객체까지 출력되면서 발생할 수 있는 순환 참조를 고려하여 `Member`와 `Car`를 `toString()` 대상에서 제외했습니다.

```java
@ToString(exclude = {"member", "car"})
```

---

## 🔐 Security

Spring Security를 활용하여 다음과 같은 인증·인가 기능을 구현했습니다.

* Form Login
* Logout
* 사용자 인증
* `ROLE_USER` / `ROLE_ADMIN` 권한 분리
* 관리자 URL 접근 제한
* BCrypt 기반 비밀번호 암호화

---

## 📚 What I Learned

이 프로젝트를 통해 다음 내용을 학습했습니다.

* Spring Boot 기반 웹 애플리케이션 개발
* Spring MVC의 Controller 기반 요청 처리
* Spring Data JPA를 활용한 데이터 접근
* Entity와 MySQL 테이블 간 매핑
* `@ManyToOne` 연관관계 설계
* `FetchType.LAZY` 지연 로딩
* 외래 키 기반 데이터 관계 설계
* Spring Security 인증 및 권한 관리
* BCrypt 비밀번호 암호화
* Thymeleaf 서버 사이드 렌더링
* Repository를 활용한 데이터 조회 및 저장

특히 기능 구현뿐만 아니라 **회원·차량·구매 데이터가 서로 어떤 관계를 가지는지 먼저 설계하고 이를 JPA를 통해 객체와 데이터베이스에 연결하는 과정**을 경험했습니다.

---

## 📂 Documentation

프로젝트 구현 과정과 결과를 정리한 기말과제 PDF를 저장소에 함께 첨부했습니다.

`프레임워크실습_TermProject_2201110351_배형권_(기말과제).pdf`

---

## 👤 Author

**배형권**

GitHub:
https://github.com/smcmfmf
