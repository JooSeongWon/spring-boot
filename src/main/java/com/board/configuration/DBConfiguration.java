package com.board.configuration;


import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@EnableTransactionManagement //스프링에서 제공하는 애너테이션 기반 트랜잭션을 활성화 사용하진않을것
@Configuration //지정한 클래스를 자바 기반의 설정 파일로 인식
@PropertySource("classpath:/application.properties") //해당 클래스에서 참조할 properties 파일의 위치를 지정
public class DBConfiguration {

	@Autowired //빈(Bean)으로 등록된 인스턴스(이하 객체)를 클래스에 주입하는 데 사용
	private ApplicationContext applicationContext; //스프링 컨테이너:빈의 생성과 사용,관계,생명 주기 관리
	
	@Bean //컨테이너에 의해 관리되는 빈으로 등록 // Configuration 클래스의 메서드 레벨에만 지정 가능
	@ConfigurationProperties(prefix = "spring.datasource.hikari") //@PropertySource에 지정된 파일에서 prefix로 시작하는 설정을 모두 읽어 들여 해당 메서드에 매핑(바인드) //클래스 레벨에도 지정 가능
	public HikariConfig hikariConfig() { //히카리CP(커넥션 풀 라이브러리) 객체 생성
		return new HikariConfig();
	}
	
	@Bean
	public DataSource dataSource() { //데이터 소스(커넥션 풀을 지원하기 위한 인터페이스) 객체 생성
		return new HikariDataSource(hikariConfig());
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception { //데이터베이스 커넥션과 SQL 실행에 대한 모든 것을 갖는 역할
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean(); //마이바티스와 스프링의 연동 모듈 //마이바티스 XML Mapper, 설정 파일 위치 저저저
		factoryBean.setDataSource(dataSource());
		factoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappers/**/*Mapper.xml")); //getResounrces메서드의 인자로 지정된 패턴에 포함되는 XML Mapper를 인식하도록 하는 역할
		factoryBean.setTypeAliasesPackage("com.board.domain");
		factoryBean.setConfiguration(mybatisConfg()); //mybatiseConfg와 관련된 빈을 설정 파일로 지정
		return factoryBean.getObject(); //SqlSessionFactoryBean 자체가 아닌, getObject 메서드가 리턴하는 SqlSessionFactory 생성
	}
	
	//SqlSessionTemplate
	//마이바티스 스프링 연동 모듈의 핵핵
	//SqlSession을 구현하고, 코드에서 SqlSession을 대체하는 역할
	//쓰레드에 안전하고, 여러 개의 DAO나 Mapper에서 공유할 수 있다
	//필요한 시점에 세션을 닫고,커밋 또는 롤백하는 것을 포함한 세션의 생명주기를 관리한다
	@Bean
	public SqlSessionTemplate sqlSession() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory());
	}
	
	//application.properties에서 mybatis.configuration으로 시작하는 모든 설정을 읽어 들여 빈으로 등록
	@Bean
	@ConfigurationProperties(prefix = "mybatis.configuration") 
	public org.apache.ibatis.session.Configuration mybatisConfg(){
		return new org.apache.ibatis.session.Configuration();
	}
	
	//스프링에서 제공해주는 트랜잭션 매니저를 빈(Bean)으로 등록
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	
}
