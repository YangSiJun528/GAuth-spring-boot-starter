# GAuth-spring-boot-starter

- - -

[MSG.Team](https://github.com/GSM-MSG)에서 제공하는
**GAuth**(광주소프트웨어마이스터고등학교 교내 OAuth2 서비스)를
**Spring Security** 환경에서 쉽게 사용할 수 있도록
**확장된 기능을 제공하는 모듈**입니다.

## 설명

이 모듈은 Spring Security의 기능을 활용하여 GAuth를 간편하게 사용할 수 있도록 개발되었습니다.    
GAuth를 Spring Security Starter에서 제공하는 OAuth2와 유사한 방식으로 편리하게 사용할 수 있습니다.   
더불어, 이 모듈은 자체 로그인, formLogin, OAuth2와 같은 기존의 다양한 인증 방식과도 원활하게 호환됩니다.

## Install

Step 1. 빌드 파일에 JitPack Repository 추가하기  
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

## 시작하기

간단한 프로젝트를 따라 구현하면서 사용법을 익히고 싶다면 튜토리얼을 참고하세요.

더 자세한 정보를 확인하고 싶으면 참조 문서와 API 문서를 읽어보세요.

## Documentation

| 모듈 버전                                                                                                 | Spring Boot 버전 | 최소 Java version | 참조 문서                                                         | API 문서                                                                                                                                           | 
|-------------------------------------------------------------------------------------------------------|----------------|-----------------|---------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------|
| [2.0.2](https://github.com/YangSiJun528/GAuth-spring-boot-starter/releases/tag/2.0.2)                 | 2.7.x 이상       | 11              | [Reference Doc.](./docs/guide/ver_202/reference/reference.md) | [API Doc.](https://htmlpreview.github.io/?https://github.com/YangSiJun528/GAuth-spring-boot-starter/blob/main/docs/guide/ver_202/api/index.html) |
| [\[Deprecated\] 1.0.1 ](https://github.com/YangSiJun528/GAuth-spring-boot-starter/releases/tag/1.0.1) | 3.x 이상         | 17              | [Reference Doc.](./docs/guide/ver_101/reference/reference.md) | ❌                                                                                                                                                |

## 튜토리얼

- ### [GAuth 시작하기](./docs/guide/tutorial/basic/index.md)
    - 가장 기본적인 사용법을 익힐 수 있습니다.
- ### [JWT 기능 추가하기](./docs/guide/tutorial/jwt/index.md)
    - 인증 전후로 필요한 커스텀로직을 추가하는 방법을 소개합니다.
    - 해당 예시에서는 인증 정보를 Database에 저장하고, JWT를 통한 인증/인가 기능을 추가합니다.

## 기여

| 제목    | 내용                                                                                |
|-------|-----------------------------------------------------------------------------------|
| 질문    | Issue Tracker에 질문을 작성합니다.                                                         |
| 버그 제보 | Issue Tracker에 제보할 버그를 작성합니다.                                                     |
| 기능 제안 | Issue Tracker에 제안하고 싶은 기능을 작성합니다.                                                 |
| 코드 기여 | GitHub에서 코드를 Fork하고, Pull Request를 보냅니다. <br/>주요한 기능을 변경하고 싶다면 기능 제안을 먼저 진행해주십시오. |

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
