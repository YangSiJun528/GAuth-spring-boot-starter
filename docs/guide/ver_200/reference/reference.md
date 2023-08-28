# Guide - ver. 2.0.0

* [Guide - ver. 2.0.0](#guide---ver-200)
    * [시작하기 전에...](#시작하기-전에)
    * [시작하기](#시작하기)
        * [1. 프로젝트 만들기](#1-프로젝트-만들기)
        * [2. 모듈 가져오기](#2-모듈-가져오기)
        * [3. 환경 설정하기](#3-환경-설정하기)
            * [Properties 정의하기](#properties-정의하기)
            * [예시](#예시)
        * [4. Security Configuration에 적용하기](#4-security-configuration에-적용하기)
            * [GAuthLoginConfigurer 옵션](#GAuthLoginConfigurer-옵션)
            * [예시 1](#예시-1)
            * [예시 2](#예시-2)

<!-- TOC -->

## 시작하기 전에...

1. GAuth-spring-boot-starter 모듈은 2가지 의존성을 필요로 합니다.
- `spring-boot-starter-security`   
- `spring-boot-starter-web`

2. GAuth 인증을 사용하기 위해 서비스를 등록하야 합니다.  
   [GAuth.co.kr](GAuth.co.kr)에 접속하여 서비스를 등록할 수 있습니다.

## 시작하기

### 1. 프로젝트 만들기

`spring-boot-starter-security`, `spring-boot-starter-web` 의존성을 가지는 Spring Boot 프로젝트를 준비합니다.

### 2. 모듈 가져오기

모듈을 가져옵니다. 자세한 내용은 [README.md의 모듈 불러오기](../../../../README.md#모듈-불러오기)를 확인하세요.

### 3. 환경 설정하기

#### Properties 정의하기

`application.properties` 혹은 `application.yml`에 GAuth 관련 설정을 정의해야 합니다.

- gauth.client-id
- gauth.client-secret
- gauth.redirect-uri

##### 예시

application.properties

```properties
gauth.security.client-id:1234567890qwertyuiop
gauth.security.client-secret:1234567890qwertyuiop1234567890qwertyuiop
gauth.security.redirect-uri:http://localhost:8080/login/gauth/code
```

### 4. Security Configuration에 적용하기

Spring Security의 Configuration에 관한 내용은 [공식문서](https://docs.spring.io/spring-security/reference/index.html)를 참고하세요.

Spring Security의 SecurityFilterChain에 Gauth 설정을 위한 `GAuthLoginConfigurer`를 등록해야 합니다.

#### GAuthLoginConfigurer 옵션

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

#### 예시 1

간단한 등록만으로 GAuth 인증을 사용할 수 있습니다.

```java

@Configuration
public class SecurityConfig {
    private final GAuthLoginConfigurer gatuh;

    public SecurityConfig(GAuthLoginConfigurer gAuthLoginConfigurer) {
        this.gatuh = gAuthLoginConfigurer;
    }
    // ...

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // ...
                .apply(gatuh);
        return http.build();
    }
}
```

#### 예시 2

Endpoint나 Handler를 Custom 할 수 있습니다.

```java

@Configuration
public class SecurityConfig {
    private final GAuthLoginConfigurer gatuh;

    public SecurityConfig(GAuthLoginConfigurer gAuthLoginConfigurer) {
        this.gatuh = gAuthLoginConfigurer;
    }
    // ...

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // ...
                .apply(gatuh
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
