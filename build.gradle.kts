import java.util.*

plugins {
    java
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.flexsible"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot & R2DBC 관련 의존성
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.mariadb:r2dbc-mariadb:1.1.4")

    // Lombok 의존성
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // 테스트 의존성
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // OS에 따라 Netty 네이티브 라이브러리 추가
    val osName = System.getProperty("os.name").lowercase(Locale.getDefault())
    val osArch = System.getProperty("os.arch")

    if (osName.contains("mac")) {
        if (osArch == "aarch64") {
            implementation("io.netty:netty-resolver-dns-native-macos:4.1.96.Final:osx-aarch_64")
        } else if (osArch == "x86_64") {
            implementation("io.netty:netty-resolver-dns-native-macos:4.1.96.Final:osx-x86_64")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    // 테스트를 빌드에서 제외
    enabled = false
}
