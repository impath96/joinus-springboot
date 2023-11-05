package com.joinus.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardTotalBean {
	
	private MembersVo membersVo;
	private ClubBoardsVo clubBoardsVo;
	private ClubsVo clubsVo;
	private BoardTypesVo boardTypesVo;
	private BoardCommentsVo boardCommentsVo;
	private BoardLikesVo boardLikesVo;

}
