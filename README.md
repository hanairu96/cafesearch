# [개인 프로젝트] CafeSearch
- 네이버 API를 통해 카페를 검색하고 리뷰를 작성할 수 있는 사이트입니다.
- Spring Boot와 JPA를 이용해 구현했습니다.

## 1. 제작 기간
- 기간: 2023.08.28~2023.11.17

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

## 3. 화면
<img src="https://github.com/hanairu96/cafesearch/assets/118409554/f97bd89a-dd04-4e93-8398-d23cfbb7518d"/>

## 4. ERD 설계
<img src="https://github.com/hanairu96/cafesearch/assets/118409554/b68f154a-97c2-4f77-b977-f2b79bbd59e9"/>

- ERD는 ERDCloud 사이트를 사용했습니다.

## 5. 구현한 기능
- 회원가입
- 로그인/로그아웃
- OAuth 소셜 로그인(구글, 네이버, 카카오)
- 스프링 시큐리티 적용
- 카페 검색(네이버 검색 API)
- 지도에 카페 위치 표시(네이버 지도 API)
- 리뷰 작성, 수정, 삭제

## 6. 구현한 기능 설명
<details>
  <summary><b>1. Naver API를 통한 지역 검색</b></summary>

####
- 검색 요청 및 응답에 사용할 DTO 클래스들을 만들고 RestTemplate을 이용해 Naver API 서버로부터 검색 결과를 받아온다.
- application.yaml
  - [Naver Developers](https://developers.naver.com/main/) 애플리케이션 등록 후 Client ID, Client Secret을 받아온다.
  - application.yaml에 Client ID, Client Secret 및 Naver API의 검색 요청 URL을 작성한다.
- [SearchLocalReq.java](https://github.com/hanairu96/cafesearch/blob/master/src/main/java/com/toy/cafesearch/naver/dto/SearchLocalReq.java)
  - 검색 요청에 사용할 DTO 클래스
  - [Documents](https://developers.naver.com/docs/serviceapi/search/local/local.md#%EC%A7%80%EC%97%AD)에 적혀 있는 대로 검색시 필요한 파라미터를 설정한다.
  - 요청할 내용을 Map 형식으로 반환해주는 toMultiValueMap() 메소드를 작성한다.
- [SearchLocalRes.java](https://github.com/hanairu96/cafesearch/blob/master/src/main/java/com/toy/cafesearch/naver/dto/SearchLocalRes.java)
  - Documents에 적혀 있는 대로 응답에 필요한 요소들을 작성한다.
- [NaverClient.java](https://github.com/hanairu96/cafesearch/blob/master/src/main/java/com/toy/cafesearch/naver/NaverClient.java)
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

## 7. 트러블 슈팅
<details>
  <summary><b>1. Spring Security 버전 업으로 인한 변경 문제</b></summary>

#### 문제
- Spring Boot 3.0 이상의 버전을 사용하면 Spring Security 6.0 이상의 버전을 사용해야 함
- Spring Security 6.0 이상의 버전에서는 이전 버전에서 사용되던 것들이 deprecated 또는 삭제된 것이 많았음
  - 이전 버전에서는 WebSecurityConfigurerAdapter 클래스를 상속받고 configure(HttpSecurity http) 메소드를 오버라이드하여 설정해야 했으나 현재 버전에서는 삭제됨
  - 그리고 and() 메소드를 이용한 메소드 체이닝 방식도 deprecated 됨
  - authorizeRequests(), antMatchers() 메소드도 deprecated 됨
#### 해결
- configure(HttpSecurity http) 메소드 대신 filterChain(HttpSecurity http) 메소드를 사용하고 http.build()를 return 함
- and() 메소드 대신 함수형으로 코드를 작성함
- authorizeHttpRequests(), requestMatchers() 메소드를 사용함

<div markdown="1">

```java
//기존 코드 예시
@Configuration 
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .and()
            .authorizeRequests() 
            .antMatchers("/main").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/loginPage")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/main")
            .and()
            .logout();
    }
}
```

</div>
<div markdown="1">

```java
//변경된 코드 예시
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
            .authorizeHttpRequests(request -> request
                .requestMatchers("/main").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/loginPage")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/main")
            )
            .logout(withDefaults());;
        return http.build();
    }
}
```

</div>
</details>

<details>
  <summary><b>2. Entity DBMS 전략 문제</b></summary>

#### 문제
- REIVEW 테이블의 PK인 REVIEW_NO가 NULL일 때 자동으로 값이 들어가게 하려고 함
- 그래서 Entity 클래스인 Review의 reviewNo 필드에 @GeneratedValue(strategy = GenerationType.IDENTITY)를 붙임
- 그런데 리뷰를 추가하는 메소드를 실행했더니 NULL을 삽입할 수 없다는 OracleDatabaseException이 발생함
#### 해결
- DBMS에 맞는 전략이 아니었기 때문에 예외가 발생하는 것이었음
- IDENTITY 전략은 시퀀스가 없는 MySQL 같은 DBMS에서 사용함
- 반면 이 프로젝트에서 사용하는 Oracle DB에서는 SEQUENCE 전략을 사용해야 함
- 그래서 @GeneratedValue의 전략을 수정하고 @SequenceGenerator도 추가로 작성하니 정상 작동함

<div markdown="1">

```java
@Entity
@SequenceGenerator(
        name = "REVIEW_SEQ_GEN",
        sequenceName = "REVIEW_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Review {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "REVIEW_SEQ_GEN"
    )
    @Column(name = "review_no")
    private int reviewNo;
}
```

</div>
</details>

<details>
  <summary><b>3. Ajax에서 PUT 요청이 안 되는 문제</b></summary>

#### 문제
- Ajax로 데이터를 서버에 보내서 수정하는 PUT 요청을 했더니 400 에러 발생
#### 해결
- 보안 때문에 content-type이 "application/x-www-form-urlencoded"인 경우 GET, POST로만 요청을 보낼 수 있다고 함
- PUT, DELETE의 경우 application/json, application/xml과 같이 '*/json', '*/xml'으로 contentType을 지정해줘야 함
- contentType을 JSON으로 지정하고, 보내려는 데이터를 JSON 형태로 만들어주니 해결됨

<div markdown="1">

```javascript
$.ajax({
  url: "/cafe/review/",
  type: "put",
  data: JSON.stringify(putElements),
  contentType: "application/json;charset=UTF-8",
  success: data => {
    alert("수정되었습니다.");
    location.replace(refer);
  },
  error: e => {
    alert("수정에 실패하였습니다.");
  }
});
```

</div>
</details>
