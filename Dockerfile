# 사용 가능한 최신 자바 21 기반 이미지로 시작
FROM openjdk:21-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# 애플리케이션 JAR 파일을 컨테이너로 복사
COPY build/libs/concurrency-0.0.1-SNAPSHOT.jar app.jar

# 애플리케이션을 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

# 애플리케이션이 사용하는 포트 열기
EXPOSE 8080
