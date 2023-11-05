package com.joinus.domain;

import java.sql.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembersVo {

	private int member_no;
	
	@NotBlank(message = "이메일을 입력해주세요.")
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	private String member_email;
	
	@NotBlank(message = "비밀번호를 입력해주세요")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 8~20자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	private String member_pass;
	
	@NotBlank(message = "이름을 입력해주세요.")
	private String member_name;
	
	private String member_tel;
	private String member_image;
	private Date member_regdate;
	private Date member_updatedate;
	private String member_authority;
	private String member_signup_type;
	private String member_status;
	private String member_location;

}
