# spring-boot project
스프링부트로 게시판 만들기 

화면 처리 : Thymeleaf

프로젝트 설정 : Java 기반 설정

데이터베이스 : MySQL

퍼시스턴스 프레임워크 : MyBatis


## 개발환경
STS(이클립스) 4.13.1

Spring Boot 2.6.3

Java 11

Thymeleaf 3.0

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
* 원인 : sql문 작성할 때 오타로 #대신 $를 작성해서 발생

**ibatis.reflection.ReflectionException: There is no getter for property named 'notice_yn' in 'class com.board.domain.BoardDTO'**
* 원인 : parameterType의 property명과 #{}안의 변수명이 달라서 발생

**The operator == is undefined for the argument type(s) long, null**
* if (params.getIdx() == null) {} 부분에서 에러 발생
* 원인 : BoardDTO에서 idx 타입이 Long타입이 아니라 long타입으로 되어있었음
* Long은 long의 Wrapper Class로 객체 타입으로 래퍼 객체는 내부의 값을 비교할 때 == 연산자 사용 불가 (래퍼클래스와 기본 자료형 비교는 ==/equals 둘다 가능)

**thymeleaf layout**
* 타임리프 1.x 일때 layout:decorator="layout경로"
* 타임리프 2.x 일때 layout:decorate="~{layout경로}"
* 스프링 부트 2.6 이상(타임리프3)일때 layout:decorate="~{layout경로}"

