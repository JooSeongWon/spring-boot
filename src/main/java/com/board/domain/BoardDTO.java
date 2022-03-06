package com.board.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

//롬복: 도메인 클래스에서 getter/setter, toString, equals 등의 메서드를 에너테이션으로 사용하게 하는 라이브러리
@Getter
@Setter
public class BoardDTO extends CommonDTO {

	private Long idx; //번호 (PK)
	private String title; //제목
	private String content; //내용
	private String writer; //작석자
	private int viewCnt; //조회수
	private String noticeYn; //공지 여부
	private String secretYn; //비밀 여부
	
	//공통 멤버 변수 CommonDTO에 추가했음
	/*private String deleteYn; //삭제 여부
	private LocalDateTime insertTime; //등록일
	private LocalDateTime updateTime; //수정일
	private LocalDateTime deleteTime; //삭제일*/
	
}
