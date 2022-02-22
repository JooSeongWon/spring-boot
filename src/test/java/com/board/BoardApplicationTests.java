package com.board;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class BoardApplicationTests {

	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private SqlSessionFactory sessionFactory;
	
	@Test
	void contextLoads() {
	}

	@Test
	public void testByApplicationContext() { //DBConfiguration 클래스의 sqlSessionFactory 메서드의 이름을 ApplicationContext의 getBean 메서드의 인자로 지정
			try {
				System.out.println("-------------------");
				System.out.println(context.getBean("sqlSessionFactory"));
				System.out.println("-------------------");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	}	
	
	@Test
	public void testBySqlSessionFactory() {
			try {
				System.out.println("-------------------");
				System.out.println(sessionFactory.toString());
				System.out.println("-------------------");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
}
