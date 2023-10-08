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
- 검색 요청 및 응답에 사용할 DTO 클래스들을 만들고 RestTemplate을 이용해 Naver API 서버로부터 검색 결과를 받아온다.
- application.yaml
  - [Naver Developers](https://developers.naver.com/main/) 애플리케이션 등록 후 Client ID, Client Secret을 받아온다.
  - application.yaml에 Client ID, Client Secret 및 Naver API의 검색 요청 URL을 작성한다.
- SearchLocalReq.java
  - 검색 요청에 사용할 DTO 클래스
  - [Documents](https://developers.naver.com/docs/serviceapi/search/local/local.md#%EC%A7%80%EC%97%AD)에 적혀 있는 대로 검색시 필요한 파라미터를 설정한다.
  - 요청할 내용을 Map 형식으로 반환해주는 toMultiValueMap() 메소드를 작성한다.
- SearchLocalRes.java
  - Documents에 적혀 있는 대로 응답에 필요한 요소들을 작성한다.
- NaverClient.java
  - Client ID, Client Secret 및 검색 요청 URL을 가져오는 변수를 작성한다.
  - 표현식 기반으로 다른 파일의 값을 가져오는 @Value 어노테이션으로 yaml의 값을 주입한다.
  - 검색 요청 내용을 Naver API 서버에 보내서 응답 받는 searchLocal(SearchLocalReq searchLocalReq) 메소드를 작성한다.
    - 호출할 서버 URL과 요청할 내용을 UriComponentsBuilder 클래스의 메소드 파라미터로 설정하여 URI 타입의 객체를 생성한다.
    - 헤더 값을 설정할 수 있는 HttpHeaders 클래스에 Client-Id와 Client-Secret, 그리고 ContentType을 설정한다.
    - 헤더를 파라미터로 하여 HttpEntity 타입의 객체를 생성한다.
    - 응답 DTO 클래스인 SearchLocalRes는 제네릭 타입의 필드를 가지기에 타입 안정성을 위해 ParameterizedTypeReference 클래스로 객체를 생성한다.
    - 다른 서버와의 통신에 사용되는 RestTemplate 클래스의 exchange() 메소드로 ResponseEntity 타입의 객체를 생성한다.
    - ResponseEntity의 getBody()로 응답 DTO 클래스인 SearchLocalRes 타입의 객체를 반환한다.
- 이후 Service 단에서 검색어를 설정하여 searchLocal() 메소드로 결과를 받고 그 결과 객체를 가공하여 사용한다.

</details>

