<!-- TOC start (generated with https://github.com/derlin/bitdowntoc) -->

- [GAuth Spring - Reference Documentation](#gauth-spring-reference-documentation)
    * [목차](#)
    * [사용조건](#-1)
    * [사용법](#-2)
        + [모듈 가져오기](#-)
        + [Configuration 설정하기](#configuration-)
            - [Properties Value 할당하기](#properties-value-)
                * [예시](#-3)
        + [GAuthLoginConfigurer Bean 등록하기](#gauthloginconfigurer-bean-)
            - [예시 1](#-1)
            - [예시 2](#-2)
        + [GAuthLoginConfigurer 옵션](#gauthloginconfigurer-)
        + [인증 과정에 커스텀로직 추가하기](#--1)
            - [예시](#-4)

<!-- TOC end -->

<!-- TOC --><a name="gauth-spring-reference-documentation"></a>
# GAuth Spring - Reference Documentation

<!-- TOC --><a name=""></a>
## 목차

<!-- TOC --><a name="-1"></a>
## 사용조건

1. Spring Boot 2.x 버전(3.x 버전 불가능), Java 11 버전 이상

2. GAuth-spring-boot-starter 모듈은 2가지 의존성을 필요로 합니다.

- `spring-boot-starter-security`
- `spring-boot-starter-web`

3. GAuth 인증을 사용하기 위해 서비스를 등록하야 합니다.  
   [GAuth.co.kr](GAuth.co.kr)에 접속하여 서비스를 등록할 수 있습니다.

<!-- TOC --><a name="-2"></a>
## 사용법

<!-- TOC --><a name="-"></a>
### 모듈 가져오기

모듈을 가져옵니다.   
자세한 내용은 [README.md의 Install](../../../../README.md)를 확인하세요.

<!-- TOC --><a name="configuration-"></a>
### Configuration 설정하기

<!-- TOC --><a name="properties-value-"></a>
#### Properties Value 할당하기

`application.properties` 혹은 `application.yml`에 GAuth 관련 설정을 정의해야 합니다.

- gauth.security.client-id
- gauth.security.client-secret
- gauth.security.redirect-uri

<!-- TOC --><a name="-3"></a>
##### 예시

application.properties

```properties
gauth.security.client-id:1234567890qwertyuiop
gauth.security.client-secret:1234567890qwertyuiop1234567890qwertyuiop
gauth.security.redirect-uri:http://localhost:8080/login/gauth/code
```

<!-- TOC --><a name="gauthloginconfigurer-bean-"></a>
### GAuthLoginConfigurer Bean 등록하기

Spring Security의 Configuration에 관한 내용은 [Spring Security 공식문서](https://docs.spring.io/spring-security/reference/index.html)를 참고하세요.

Spring Security의 SecurityFilterChain에 Gauth 설정을 위한 `GAuthLoginConfigurer`를 등록해야 합니다.

<!-- TOC --><a name="-1"></a>
#### 예시 1

간단한 등록만으로 GAuth 인증을 사용할 수 있습니다.

```java

@Configuration
public class SecurityConfig {
    private final GAuthLoginConfigurer gauth;

    public SecurityConfig(GAuthLoginConfigurer gAuthLoginConfigurer) {
        this.gauth = gAuthLoginConfigurer;
    }
    // ...

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // ...
                .apply(gauth);
        return http.build();
    }
}
```

<!-- TOC --><a name="-2"></a>
#### 예시 2

Endpoint나 Handler를 변경할 수 있습니다.

```java

@Configuration
public class SecurityConfig {
    private final GAuthLoginConfigurer gauth;

    public SecurityConfig(GAuthLoginConfigurer gAuthLoginConfigurer) {
        this.gauth = gAuthLoginConfigurer;
    }
    // ...

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // ...
                .apply(gauth
                        .loginPageUrl("/to-gauth-login-page")
                        .loginProcessingUrl("/login/code/gauth")
                        .successHandler(
                                new SimpleUrlAuthenticationSuccessHandler("/success"))
                        .failureHandler(
                                new SimpleUrlAuthenticationFailureHandler("/failure")));
        return http.build();
    }
}
```

<!-- TOC --><a name="gauthloginconfigurer-"></a>
### GAuthLoginConfigurer 메서드

- `loginPageUrl(String loginPageUrl)`
    - GAuth 로그인 페이지로 리다이렉트되는 주소를 설정합니다.
    - default: `/gauth/authorization`
- `loginProcessingUrl(String loginProcessingUrl)`
    - GAuth 인증 코드를 받아 처리하는 필터에 접근하는 주소를 설정합니다.
    - default: `/login/code/gauth`
- `successHandler(AuthenticationSuccessHandler successHandler)`
    - GAuth 인증을 성공했을 때, 실행되는 `AuthenticationSuccessHandler`를 정의합니다.
    - default: 서버의 root 주소로 리다이렉트 됩니다.
- `failureHandler(AuthenticationFailureHandler failureHandler)`
    - GAuth 인증을 실패했을 때, 실행되는 `AuthenticationFailureHandler`를 정의합니다.
    - default: 인증이 실패했다는 json과 함께 401 HTTP Status를 반환합니다.
- `disable()`
    - GAuth 인증을 사용하지 않도록 설정합니다.

<!-- TOC --><a name="--1"></a>
### 인증 과정에 커스텀로직 추가하기

JWT를 인증,인가 수단으로 사용하거나 Database에 인증 정보를 저장하는 것과 같은 추가적인 로직이 필요할 수 있습니다.   
추가적인 로직을 정의하기 위해선 커스텀 로직이 추가된 [`GAuthUserService`](#아키텍처 설명에 생길듯)의 구현체를 만들고 Bean으로 등록해야 합니다.

기본적으로 사용되는 `DefaultGAuthUserService`를 사용하여 인증 과정을 위임하고, 추가적인 로직을 작성하는 방법을 추천합니다.

구체적인 구현 방식은 아래의 예시나 [JWT 기능 추가하기](./../../../../docs/guide/tutorial/jwt/index.md) 튜토리얼을 참고하세요.

<!-- TOC --><a name="-4"></a>
#### 예시

이 글에서는 간단하게 InMemoryDB(Map 객체 사용)에 인증 정보를 저장하는 로직을 추가해 보겠습니다.

먼저 InMemoryDB 역할을 할 객체인 `GAuthRepository`를 정의하였습니다.

```java
public class GAuthRepository {
    private final Map<String, GAuthUser> repo = new HashMap<>();

    public GAuthRepository() {
    }

    public void save(String key ,GAuthUser gauthUser) {
        repo.put(key, gauthUser);
    }
}

```

`GAuthUserService` Interface를 상속하는 구현체`CustomGAuthUserService`를 만들고 인증 과정은 `DefaultGAuthUserService`에 위임합니다.

```java
public class CustomGAuthUserService
        implements GAuthUserService<GAuthAuthorizationRequest, GAuthUser> {

    private final GAuthUserService delegateService = new DefaultGAuthUserService();
    private final GAuthRepository gauthRepository;

    public CustomGAuthUserService(GAuthRepository gauthRepository) {
        this.gauthRepository = gauthRepository;
    }

    @Override
    public GAuthUser loadUser(GAuthAuthorizationRequest userRequest)
            throws GAuthAuthenticationException {
        GAuthUser gauthUser = delegateService.loadUser(userRequest);
        return gauthUser;
    }
}
```
`CustomGAuthUserService`에 인증 정보를 `GAuthRepository`에 저장하는 로직을 추가합니다.

```java
public class CustomGAuthUserService
        implements GAuthUserService<GAuthAuthorizationRequest, GAuthUser> {

    // 생략

    @Override
    public GAuthUser loadUser(GAuthAuthorizationRequest userRequest)
            throws GAuthAuthenticationException {
        GAuthUser gauthUser = delegateService.loadUser(userRequest);
        gauthRepository.save(gauthUser.getName(), gauthUser); // DB에 User 정보를 저장하는 커스텀로직 추가
        return gauthUser;
    }
}
```

`CustomGAuthUserService` 객체를 Bean으로 등록합니다.

**`@Configuration`과 `@Bean` 어노테이션을 사용한 Bean 등록 예시**
```java
@Configuration
public class GAuthConfig {

    public GAuthConfig() {
    }

    @Bean
    public GAuthUserService<GAuthAuthorizationRequest, GAuthUser> customGAuthUserService() {
        return new CustomGAuthUserService();
    }
}
```

**`@Component` 어노테이션을 사용한 Bean 등록 예시**
```java
@Component
public class CustomGAuthUserService
        implements GAuthUserService<GAuthAuthorizationRequest, GAuthUser> {
    // 생략
}
```
