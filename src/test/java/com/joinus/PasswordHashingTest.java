package com.joinus;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.joinus.util.SHA256;

public class PasswordHashingTest {
	
	@Test
	public void hashTest() throws NoSuchAlgorithmException {
		
		String password = SHA256.encrypt("비밀번호");
		System.out.println(password);
	}
	
	// @Test
	public void parse() {
		String location = "부산시 사하구 감천동 ~~아파트 201";
		String[] part = location.split(" ");
		String savedLocation = part[0];
		for(int i = 1; i<3; i++) {
			savedLocation += " " + part[i];
		}
		System.out.println(savedLocation);
		
	}



}
