package com.joinus.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class MemberDaoTest {
	
	@Autowired
	MemberDao memberDao;
	
	@Test
	public void getMemberAll() {
		
//		List<MembersVo> members = memberDao.getMemberAll();
		
//		log.info("members : {}", members);
		
	}

}
