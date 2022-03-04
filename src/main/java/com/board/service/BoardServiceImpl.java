package com.board.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;
	
	//게시글 생성,수정을 게시글 번호(idx)의 유무를 기준으로 insert 또는 update를 실행
	@Override
	public boolean registerBoard(BoardDTO params) {
		int queryResult = 0;
		
		//params의 idx가 null이면 게시글 생성, idx가 포함되어 있으면 게시글 수정
		//null일 때 MySQL의 AUTO_INCREMENT 속성에 의해 PK(idx)가 자동 증가되어 게시글 생성
		if (params.getIdx() == null) {
			queryResult = boardMapper.insertBoard(params);
		} else {
			queryResult = boardMapper.updateBoard(params);
		}

		return (queryResult == 1) ? true : false;
	}

	//하나의 게시글을 조회하는 selectBoardDetail 메서드의 결과값을 반환
	@Override
	public BoardDTO getBoardDetail(Long idx) {
		return boardMapper.selectBoardDetail(idx);
	}

	//특정 게시글을 조회하고 사용 중인 상태의 게시글인 경우에만 게시글 삭제
	@Override
	public boolean deleteBoard(Long idx) {
		int queryResult = 0;
		
		BoardDTO board = boardMapper.selectBoardDetail(idx);
		
		if (board != null && "N".equals(board.getDeleteYn())) {
			queryResult = boardMapper.deleteBoard(idx);
		}

		return (queryResult == 1) ? true : false;
	}

	//삭제되지 않은 전체 게시글 조회
	@Override
	public List<BoardDTO> getBoardList() {
		
		//NUE 방지를 위해 비어있는 리스트 선언
		List<BoardDTO> boardList = Collections.emptyList();
		
		//사용 중인 전체 게시글 수 카운팅 한 결과 저장
		int boardTotalCount = boardMapper.selectBoardTotalCount();
		
		//사용 중인 전체 게시글이 1개 이상이면 boardList에 결과값 반환
		if (boardTotalCount > 0) {
			boardList = boardMapper.selectBoardList();
		}
		
		return boardList;
	}

}
