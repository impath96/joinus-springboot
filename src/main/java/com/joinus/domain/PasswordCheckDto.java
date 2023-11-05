package com.joinus.domain;

import java.security.NoSuchAlgorithmException;

import com.joinus.util.SHA256;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class PasswordCheckDto {

	private String currentPassword;
	private String newPassword;
	private String newPasswordConfirm;

	// 전달받은 패스워드와 매개변수로 전달받은 패스워드(실제 사용자꺼)가 동일한가?
	public boolean equalsPassword(String password) {
		String encryptedPass = "";
		try {
			encryptedPass = SHA256.encrypt(currentPassword);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (encryptedPass.equals(password)) {
			return true;
		}
		return false;
	}

	// 전달받은 새로운 비밀번호와 재확인용 새 비밀번호가 동일한가?
	public boolean equalsNewPasswordAndConfirm() {
		if (newPassword.equals(newPasswordConfirm) && !newPassword.equals("") && !newPasswordConfirm.equals("")) {
			return true;
		}
		return false;
	}

}
