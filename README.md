# GAuth-spring-boot-starter

- - -

[MSG.Team](https://github.com/GSM-MSG)에서 제공하는 **GAuth**(광주소프트웨어마이스터고등학교 교내 OAuth 서비스)를 **Spring Security**(+ Spring
Boot) 환경에서 쉽게 사용할 수 있도록 **확장된 기능을 제공하는 모듈**입니다.

## [설명](#설명)

GAuth는 좋은 서비스이지만, Spring Security 환경에서 사용하기에는 어려움이 있습니다.
이 모듈은 Spring Security의 기능을 활용하여 GAuth를 쉽게 사용할 수 있도록 개발되었습니다.
GAuth를 OAuth와 유사한 방식으로 적용하면서 많은 노력을 줄일 수 있습니다.   
또한, 기존의 자체로그인, formLogin, OAuth2 인증과도 문제없이 호환됩니다.

이 모듈에서 제공하는 주요한 기능은 아래와 같습니다.

- GAuth 로그인 페이지 자동 리다이렉트
- GAuth 코드를 입력받아 GAuth 사용자 정보 가져오기
- GAuth 인증 성공/실패 시 핸들러 적용
- 로그인/인증/리다이렉트 주소를 및 핸들러를 간편하게 지정 가능

<details>
<summary>간단한 사용 방법 예시</summary>

#### 예시 1

```java
@Configuration
public class SecurityConfig {
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

#### 예시 2

```java
@Configuration
public class SecurityConfig {
    // 생략
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

</details>

더 자세한 사용방법을 알고 싶으시다면 아래의 [가이드](#버전-별-가이드)나 [샘플 프로젝트](https://github.com/YangSiJun528/GAuth-spring-boot-starter-sample)를
확인하세요.

### 주의점

해당 모듈은 아래와 같은 버전부터 지원됩니다.

- Java 17 이상
- Spring Boot 3.1.0 이상

또한, Spring MVC 환경을 기준으로 제작되었으며 WebFlux와 같은 Non-Blocking 서비스에선 잘 동작하지 않을 수 있습니다. (0.0.1 기준)

## [시작하기](#시작하기)

### [모듈 불러오기](#모듈-불러오기)

Step 1. 빌드 파일에 JitPack 리포지토리 추가하기  
Step 2. 의존성 추가

#### gradle

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}
```

```groovy
dependencies {
    implementation 'com.github.YangSiJun528:GAuth-spring-boot-starter:{Version}'
}
```

#### gradle.kts

```groovy
repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}
```

```groovy
dependencies {
    implementation("com.github.YangSiJun528:GAuth-spring-boot-starter:{Version}")
}
```

#### maven

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

```xml
<dependency>
    <groupId>com.github.YangSiJun528</groupId>
    <artifactId>GAuth-spring-boot-starter</artifactId>
    <version>{Version}</version>
</dependency>
```

## [버전 별 가이드](#버전-별-가이드)

- #### [1.0.1](docs/guide/ver_100/index.md) 

## [기여하기](#기여하기)

버그 제보: 이슈 트래커에 제보할 버그를 작성합니다.  
기능 제안: 이슈 트래커에 제안하고 싶은 기능을 작성합니다.  
코드 기여: GitHub에서 코드를 Fork하고, Pull Request를 보냅니다.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
