package com.joinus.persistence;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joinus.domain.InterestsVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class InterestDaoTest {

	@Autowired
	InterestDao interestDao;
	
	private static final Logger log = LoggerFactory.getLogger(InterestDaoTest.class);

	@Test
	public void 주관심사출력() {
		List<InterestsVo> list =interestDao.selectInterestAll();
		log.info("주 관심사 리스트 : {}", list);
		
	}
}
