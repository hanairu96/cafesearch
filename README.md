# [개인 프로젝트] CafeSearch
- 네이버 API를 통해 카페를 검색하고 리뷰를 작성할 수 있는 사이트입니다.
- Spring Boot와 JPA를 이용해 구현했습니다.

## 1. 제작 기간
- 기간: 2023.08.28~

## 2. 개발 환경
- 언어: Java 17, JavaScript, HTML, CSS, SQL
- 프레임워크: Spring Boot v3.1.3 (Spring Framework v6.0.11), Spring Security v6.1.3
- 라이브러리: jQuery 3.6.1, Lombok, Spring Data JPA
- IDE: IntelliJ IDEA 2022.3.3, SQL Developer 22.2
- 빌드 도구: Gradle 8.2.1
- DBMS: Oracle Database 18c Express Edition
- WAS: Apache Tomcat v10.1.12
- 템플릿 엔진: Thymeleaf
- 버전 관리: GitHub

## 3. ERD 설계
<img src="https://github.com/hanairu96/cafesearch/assets/118409554/86ffd19d-4fc2-4ee5-b389-7ae5cfe2aafe"/>

- ERD는 ERDCloud 사이트를 사용했습니다.

## 4. 구현한 기능
- 회원가입
- 로그인/로그아웃
- 스프링 시큐리티 적용
- 카페 검색(네이버 검색 API)
- 지도에 카페 위치 표시(네이버 지도 API)
- 리뷰 작성, 수정, 삭제

## 5. 구현한 기능 설명
<details>
  <summary><b>1. Naver API를 통한 지역 검색</b></summary>

####
- 내용
</details>

<details>
  <summary><b>2. Spring Security Config 설정</b></summary>

####
- 내용
</details>

## 6. 트러블 슈팅
<details>
  <summary><b>1. Entity DBMS 전략 문제</b></summary>

#### 문제
- 내용
#### 해결
- 내용

<div markdown="1">

```java
public class Name {
	private int field;
}
```

</div>
</details>

<details>
  <summary><b>2. Ajax에서 put 사용이 안 되는 문제</b></summary>

#### 문제
- 내용
#### 해결
- 내용

</details>

<details>
  <summary><b>3. JS에서의 Thymeleaf 사용</b></summary>

#### 문제
- 내용
#### 해결
- 내용

</details>
