package com.board;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils; //다른거 선택 안하도록 주의

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
class MapperTests {

	@Autowired
	private BoardMapper boardMapper; //Autowired를 이용해서 BoardMapper 인터페이스 빈을 주입
	
//	@Test
//	public void testOfInsert() {
//		BoardDTO params = new BoardDTO();
//		params.setTitle("1번 게시글 제목");
//		params.setContent("1번 게시글 내용");
//		params.setWriter("테스터");
//		
//		int result = boardMapper.insertBoard(params);
//		System.out.println("결과는" + result + "입니다");
//	}
	
	@Test
	public void testMultipleInsert() {
		for (int i=2; i <=50; i++) {
			BoardDTO params = new BoardDTO();
			params.setTitle(i + "번 게시글 제목");
			params.setContent(i +"번 게시글 내용");
			params.setWriter(i + "번 게시글 작성자");
			boardMapper.insertBoard(params);
		}
	}
	
	@Test
	public void testOfSelectDetail() {
		BoardDTO board = boardMapper.selectBoardDetail((long) 1);
		
		try {
			//String boardJson = new ObjectMapper().writeValueAsString(board);
			//board에 저장된 게시글 정보를 Jackson 라이브러리를 이용해서 JSON 문자열료 변경한 뒤에 콘솔에 출력
			String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(board);
			
				System.out.println("=================================");
				System.out.println(boardJson);
				System.out.println("=================================");
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void TESTOFUPDATE() {
		BoardDTO params = new BoardDTO();
		params.setTitle("1번 게시글 제목을 수정합니다");
		params.setContent("1번 게시글 내용을 수정합니다");
		params.setWriter("홍길동");
		params.setIdx((long) 1);
		
		//result에는 UPDATE 쿼리가 실행된 횟수가 저장
		int result = boardMapper.updateBoard(params);
		
		if (result == 1) {
			BoardDTO board = boardMapper.selectBoardDetail((long) 1);
			
			try {
				String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(board);
				
					System.out.println("=============================");
					System.out.println(boardJson);
					System.out.println("=============================");
					
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}	
	}
	
	//selectBoardDetail 메서드의 WHERE 절에 delete_yn = 'N' 조건이 적용되어 있어서 null을 반환
	@Test
	public void testOfDelete() {
		int result = boardMapper.deleteBoard((long) 1);
		if (result == 1) {
			BoardDTO board = boardMapper.selectBoardDetail((long) 1);
			
				try {
					String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(board);
					
						System.out.println("============================");
						System.out.println(boardJson);
						System.out.println("============================");
						
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
		}
	}
	
	@Test
	public void testSelectList() {
		int boardTotalCount = boardMapper.selectBoardTotalCount();
		if (boardTotalCount > 0) {
			List<BoardDTO> boardList = boardMapper.selectBoardList();
			if (CollectionUtils.isEmpty(boardList) == false) {
				for (BoardDTO board : boardList) {
					System.out.println("=========================");
					System.out.println(board.getTitle());
					System.out.println(board.getContent());
					System.out.println(board.getWriter());
					System.out.println("=========================");
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
