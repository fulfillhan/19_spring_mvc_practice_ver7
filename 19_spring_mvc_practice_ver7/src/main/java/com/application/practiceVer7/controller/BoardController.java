package com.application.practiceVer7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.practiceVer7.dto.BoardDTO;
import com.application.practiceVer7.service.BoardService;

//2024.02.24 총 소요된 시간: 1시간

//비번 암호화 하기, 조회수 증가
@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@GetMapping("/createBoard")
	public String createBoard() {
		return "board/createBoard";
	}

	@PostMapping("/createBoard")
	@ResponseBody
	public String createBoard(@ModelAttribute BoardDTO boardDTO) {
		//System.out.println(boardDTO);
		boardService.insertBoard(boardDTO);

		String script = """
				<script>
				alert('게시글이 등록되었습니다.');
				location.href='/board/boardList';
				</script>
				""";
		return script;
	}

	@GetMapping("/boardList")
	public String boardList(Model model) {
		model.addAttribute("boardList", boardService.getBoardList());
		return "board/boardList";
	}

	@GetMapping("/boardDetail")
	public String boardDeatil(Model model, @RequestParam("boardId") long boardId) {
		model.addAttribute("boardDTO", boardService.getBoardDetail(boardId));
		return "board/boardDetail";
	}

	@GetMapping("/authentication")
	public String authentication(Model model, @RequestParam("boardId") long boardId,
			@RequestParam("menu") String menu) {
		model.addAttribute("boardDTO", boardService.getBoardDetail(boardId));
		model.addAttribute("menu", menu);
		return "board/authentication";
	}

	@PostMapping("/authentication")
	@ResponseBody
	public String authentication(@ModelAttribute BoardDTO boardDTO, @RequestParam("menu") String menu) {
		boolean isAuthenticated = boardService.isAuthenticated(boardDTO);
		//System.out.println(isAuthenticated);
		String script = "";
		if (isAuthenticated) {
			// 인증
			if (menu.equals("update")) {
				script = "<script>";
				script += "alert('인증되었습니다.');";
				script += "location.href='/board/updateBoard?boardId=" + boardDTO.getBoardId() + "';";
				script += "</script>";
			} else if (menu.equals("delete")) {
				script = "<script>";
				script += "alert('인증되었습니다.');";
				script += "location.href='/board/deleteBoard?boardId=" + boardDTO.getBoardId() + "';";
				script += "</script>";
			}
		} else {
			// 인증실패
			script = """
					<script>
					alert('패스워드를 재확인 해주세요');
					location.href='/board/boardDetail';
					</script>
					""";
		}
		return script;
	}

	@GetMapping("/updateBoard")
	public String updateBoard(Model model, @RequestParam("boardId") long boardId) {
		model.addAttribute("boardDTO", boardService.getBoardDetail(boardId));
		return "board/updateBoard";
	}

	@PostMapping("/updateBoard")
	@ResponseBody
	public String updateBoard(@ModelAttribute BoardDTO boardDTO) {
		boardService.updateBoard(boardDTO);
		String script = """
				<script>
				alert('게시글이 수정되었습니다.');
				location.href='/board/boardList';
				</script>
				""";
		return script;
	}

	@GetMapping("/deleteBoard")
	public String deleteBoard(Model model, @RequestParam("boardId") long boardId) {
		model.addAttribute("boardId", boardId);
		return "board/deleteBoard";
	}

	@PostMapping("/deleteBoard")
	@ResponseBody
	public String deleteBoard(@RequestParam("boardId") long boardId) {
		boardService.deleteBoard(boardId);
		String script = """
				<script>
				alert('게시글이 삭제되었습니다.');
				location.href='/board/boardList';
				</script>
				""";
		return script;
	}
}
