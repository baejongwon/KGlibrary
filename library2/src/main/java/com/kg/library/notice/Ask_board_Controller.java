package com.kg.library.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import jakarta.servlet.http.HttpSession;

@Controller
public class Ask_board_Controller {
	@Autowired Ask_board_Service ask_service;
	@Autowired
	private HttpSession session;
	

	@RequestMapping("ask_board")
	public String notice_ask(@RequestParam(value="currentPage", required = false)String cp, Model model,String search_select,String search) {
		ask_service.ask_board(search_select,search,cp,model);
		model.addAttribute("menu", "board2");
		return "notice/ask_board";
	}
	//공지사항 글쓰기
	@RequestMapping("ask_board_write")
	public String ask_board_write(
			@RequestParam(value="no", required = false, defaultValue = "0")String no,
			@RequestParam(value="ref", required = false, defaultValue = "1")String ref,
			@RequestParam(value="step", required = false, defaultValue = "0")String step, 
			@RequestParam(value="depth", required = false, defaultValue = "0")String depth,
			Model model){
		String sessionID=(String)session.getAttribute("id");
		if(sessionID==null) {
			return "redirect:login";
		}
		model.addAttribute("menu", "board2");
		model.addAttribute("no",no);
		model.addAttribute("ref",ref);
		model.addAttribute("step",step);
		model.addAttribute("depth",depth);
		return "notice/ask_board_write";
	}
	
	@PostMapping("ask_board_writeProc")
	public String noticeboard_writeProc(String no,MultipartHttpServletRequest multi) {
		String path = ask_service.ask_board_writeProc(no,multi);
		return path;
	}
	//공지사항 내용
	@RequestMapping("ask_board_Content")
	public String ask_board_Content(String no, Model model) {
		Ask_board_DTO board = ask_service.ask_board_Content(no,model);
		if(board == null) {
			return "redirect:ask_board";
		}
		model.addAttribute("menu", "board2");
		model.addAttribute("board",board);
		return "notice/ask_board_Content";
	}
	//공지사항 삭제
	@RequestMapping("ContentDeleteProc")
	public String ContentDeleteProc(String no) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		
		String msg = ask_service.ContentDeleteProc(no);
		if(msg.equals("작성자만 삭제 할 수 있습니다."))
			return "redirect:noticeboard?no="+no;
		
		return "redirect:ask_board";
	}
	//공지사항 수정
	@RequestMapping("ask_board_Modify")
	public String ask_board_Modify(String no, Model model) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		model.addAttribute("menu", "board2");
		String path = ask_service.ask_board_Modify(no, model);
		return path;
	}
	
	@PostMapping("ask_board_ModifyProc")
	public String ask_board_ModifyProc(Ask_board_DTO board, RedirectAttributes ra, MultipartHttpServletRequest multi) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		String msg = ask_service.ask_board_ModifyProc(board,multi);
		ra.addFlashAttribute("msg", msg);
		 
		if(msg.equals("게시글 수정 성공"))
			return "redirect:ask_board_Content?no="+board.getNo();
		
		return "redirect:ask_board_Modify?no="+board.getNo();
	}
	
	//공지사이드바 템플릿
	
}
