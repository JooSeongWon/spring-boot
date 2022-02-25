# spring-boot project
스프링부트로 게시판 만들기 

화면 처리 : Thymeleaf

프로젝트 설정 : Java 기반 설정

데이터베이스 : MySQL

퍼시스턴스 프레임워크 : MyBatis


## 개발환경
STS(이클립스)

Spring Boot 2.6.3

Java 11

Gradle

Develop Tools
- Spring Boot DevTools
- Lombok
- Spring Configuraion Processor

SQL
- Spring Data JPA
- Mybatis Framwork
- MySQL Driver

Template Engines
- Thymeleaf

WEB
- Spring Web

## ERROR
**java.sql.SQLSyntaxErrorException:**
* sql문 작성할 때 오타로 #대신 $를 작성해서 발생
**ibatis.reflection.ReflectionException: There is no getter for property named 'notice_yn' in 'class com.board.domain.BoardDTO'**
* parameterType의 property명과 #{}안의 변수명이 달라서 발생

