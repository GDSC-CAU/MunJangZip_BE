# 1. OpenJDK 17 기반 이미지 사용
FROM openjdk:17

# 2. Maintainer 정보 (선택 사항)
LABEL maintainer="your-email@example.com"

# 3. JAR 파일을 컨테이너 내부로 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 4. 컨테이너 실행 시 Spring Boot 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
