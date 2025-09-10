RestMall 쇼핑몰 API 구축 프로젝트

# 사용 기술
- Java 17
- Spring Boot 3.5.5
- Spring Security 6.5.3
- Mysql 8.0
- Spring Data JPA
- Docker

# DB 설계
<img width="2320" height="942" alt="RestMall" src="https://github.com/user-attachments/assets/58e6aecd-66b4-406e-ac80-c9ffea23bc91" />



# 일지

## 2025-09-06 (토)
- 기초 세팅 및 DB 설계 진행

## 2025-09-07 (일)
- 설계된 DB에 맞게 엔티티 생성
- User와 Product의 매핑 테이블을 Cart(장바구니)로 사용하기로 함

## 2025-09-08 (월)
- Security 설정 적용
- 로그인, 회원가입 api 개발(validation 추가 예정)

## 2025-09-10 (수)
- 예외처리 전역 설정
- 회원가입 validation 추가
* 로그인시 encode된 값이 authentication 객체에 들어가야 함

  
