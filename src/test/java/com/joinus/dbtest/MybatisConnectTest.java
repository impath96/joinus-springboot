package com.joinus.dbtest;

import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class MybatisConnectTest {
	
	// DataSource 객체
//	@Autowired
	private DataSource dataSource;
	
	// SqlSessionFactory 객체
//	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
//	@Test
	public void 마이바티스_연결_테스트() {
		log.info("@@@@@@@@@@@@@@@sqlSessionFactory : " + sqlSessionFactory);
		log.info("@@@@@@@@@@@@@@@dataSource : " + dataSource);
	}
	
//	@Test
	public void 마이바티스_세션_체크() {
		
		// SqlSession : sql 쿼리를 사용할 수 있는 객체
		SqlSession session = sqlSessionFactory.openSession();

		log.info("@@@@@@@@@@@@@@@session :" + session);
	}

}
