package com.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.constant.Method;
import com.board.domain.BoardDTO;
import com.board.service.BoardService;
import com.board.util.UiUtils;

@Controller
public class BoardController extends UiUtils {

	@Autowired
	private BoardService boardService;
	
	//GetMapping : 스프링 4.3버전부터 사용 가능
	//이전 스프링 : @RequestMapping(value= "...", method = RequestMethod.XXX)
	//Model 인터페이스 : 데이터를 뷰로 전달하는 데 사용
	//addAttribute : 화면(HTML)으로 데이터 전달 이름(String name, 값(Object value) 
	//HTML에서는 ${} 표현식으로 전달받은 데이터에 접
	//RequestParam : 뷰(화면)에서 전달받은 파라미터를 처리하는 데 사용
	//게시글 리스트 페이지에서 게시글 등록 페이지로 이동하면 게시글 번호(idx)는 null로 전송
	//게시글 상세 페이지에서 수정하기 버튼을 클릭하면 컨트롤ㄹ러로 게시글 번호(idx)가 파라미터로 전송되고,
	//컨트롤러는 전달받은 게시글 번호(idx)를 getBoardDetail 메서드의 인자로 전달
	//새로운 게시글을 등록하는 경우 게시글 번호가 필요하지 않기 떄문에 required 속성을 false로 지정 default값은 true
	@GetMapping(value = "/board/write.do")
	public String openBoardWrite(@RequestParam(value = "idx", required = false) Long idx, Model model) { 
		
		//게시글 번호가 전송되지 않은 경우 비어있는 객체 전달
		if (idx == null) {
			model.addAttribute("board", new BoardDTO()); 
		} else { //게시글 번호가 전송된 경우  getBoardDetail 메서드의 실행 결과(게시글 정보 포함하고 있는 객체)전달
 			BoardDTO board = boardService.getBoardDetail(idx);
			if (board == null) { //getBoardDetail 메서드의 실행 결과가 null이면 리다이렉트
				return "redirect:/board/list.do";
			}
			model.addAttribute("board", board);
		}
		
		//String과 ModelAndView는 사용자에게 보여줄 화면(HTML 경로)을 리턴 문에 지정해서 처리
		//리턴 문에 지정된 HTML 경로의 끝에는 접미사(suffix)로 확장자(".html")가 자동 연결되어 확장자 생략
		return "board/write";
	}
	
	//폼 엘리먼트의 사용자 입력 필드의 "name" 속성 값을 통해 폼 데이터를 컨트롤러 메서드의 파라미터로 전송
	//BoardDTO의 멤버 변수명과 사용자 입력 필드의 "name" 속성 값이 동일하면 params의 각 멤버 변수에 "name" 속성 값을 통해 전달된 value가 매핑
	@PostMapping(value = "/board/register.do")
	public String registerBoard(final BoardDTO params, Model model) {
		try {
			boolean isRegistered = boardService.registerBoard(params);
			
			if (isRegistered == false) {
				return showMessageWithRedirect("게시글 등록에 실패하였습니다.", "/board/list.do", Method.GET, null, model);			
			}
			
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/board/list.do", Method.GET, null, model);
		
		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list.do", Method.GET, null, model);
		}
		
		return showMessageWithRedirect("게시글 등록이 완료되었습니다.", "/board/list.do", Method.GET, null, model);
	}
	
	@GetMapping(value = "/board/list.do")
	public String openBoardList(@ModelAttribute("params") BoardDTO params,  Model model ) {
		List<BoardDTO> boardList = boardService.getBoardList(params);
		model.addAttribute("boardList", boardList);
		
		return "board/list";
	}
	
	//게시글 조회에 필요한 게시글 번호(idx)를 @RequsetParam을 써서 파라미터로 전달 받고 넘어오지 않았을 때 직접 예외 처리하기위해 false로 설정
	@GetMapping(value = "/board/view.do")
	public String openBoardDetail(@RequestParam(value="idx", required = false) Long idx, Model model) {
		
		if (idx==null) {
			// TODO => 올바르지 않은 접근이라는 메시지를 전닳하고, 게시글 리스트로 리다이렉트@@@
			return "redirect:/board/list.do";
		}
		BoardDTO board = boardService.getBoardDetail(idx);
		
		if (board==null || "Y".equals(board.getDeleteYn())) {
			// TODO => 없는 게시글이거나, 이미 삭제된 게시글이라는 메시지를 저저, 게시글 리스트로 리다이렉트
			return "redirect:/board/list.do";
		}
		model.addAttribute("board", board);
		
		return "board/view";
	}
	
	@PostMapping(value = "/board/delete.do")
	public String deleteBoard(@RequestParam(value = "idx", required = false) Long idx, Model model) {
		if (idx == null) {
			// TODO => 올바르지 않은 접근이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
			return "redirect:/board/list.do";
		}

		try {
			boolean isDeleted = boardService.deleteBoard(idx);
			if (isDeleted == false) {
				return showMessageWithRedirect("게시글 삭제에 실패하였습니다.", "/board/list.do", Method.GET, null, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/board/list.do", Method.GET, null, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list.do", Method.GET, null, model);
		}

	return showMessageWithRedirect("게시글 삭제가 완료되었습니다.", "/board/list.do", Method.GET, null, model);
	}
}
