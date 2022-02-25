package com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.board.domain.BoardDTO;

//리턴 타입
//게시글 생성, 수정, 삭제 메서드의 리턴 타입 int
//보통은 void
//서비스 영역에서 Mapper 영역의 메서드를 호출하고,
//SQL 실행에 대한 결과값을 확실학 전달받기 위해 int로 처리

@Mapper //XML Mapper에서 메서드의 이름과 일치하는 SQL 문을 찾아 실행
public interface BoardMapper {
	
	//게시글을 생성하는 INSERT 쿼리를 호출하는 메서드
	//파라미터로는 BoardDTO 클래스가 params라는 이름으로 지정
	//params에 게시글 정보가 담김
	public int insertBoard(BoardDTO params);
	
	//하나의 게시글을 조회하는 SELECT 쿼리를 호출하는 메서드
	//퀴리가 실행되면, 각 컬럼에 해당하는 결과값이 리턴 타입으로 지정된 BoardDTO 클래스의 멤버 변수에 매핑됨
	//파라미터로는 게시글 번호(idx), 즉 PK를 전달받으며,
	//WHERE 조건으로 idx를 사용해서 특정 게시글을 조회
	public BoardDTO selectBoardDetail(Long idx);
	
	//게시글을 수정하는 UPDATE 쿼리를 호출하는 메서드
	//파라미터로는 BoardDTO 클래스가 params라는 이름으로 지정
	//params에 게시글 정보가 담김
	public int updateBoard(BoardDTO params);
	
	//게시글을 삭제하는 DELETE 쿼리를 호출하는 메서드
	//delete_yn 컬럼 : 실제로 데이터(레코드)를 삭제하지 않고, 컬럼의 상태 값을 'Y' 또는 'N'으로 지정해서
	//'N'을 지정된 데이터만 노출하게끔 하는 역할
	//->중요한 데이터를 실수로 DELETE 하는 것 방지
	//파라미터로는 게시글 번호(idx)를 전달받으며,
	//WHERE 조건으로 idx를 사용해서 특정 게시글을 삭제(상태 값 변경)
	public int deleteBoard(Long idx);
	
	//게시글 목록을 조회하는 SELECT 쿼리를 호출하는 메서드
	//리턴 타입으로 지정된 List<BoardDTO>와 같이 "<>"안에 타입을 파라미터로 갖는 형태 -> 제네릭 타입
	//리스트 안에 하나의 게시글을 조회하는 selectBoardDetail 메서드를 호출한 결과를 여러 개 저장하는 것과 유사
	public List<BoardDTO> selectBoardList();
	
	//삭제 여부(delete_yn)가 'N'으로 지정된 게시글의 개수를 조회하는 SELECT 쿼리를 호출하는 메서드
	//페이징 처리를 진행하면서 사용
	public int selectBoardTotalCount();
	
}
