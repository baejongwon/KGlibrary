package com.kg.library.guide;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class GuideController {
	
	//공지사이드바 템플릿
	@RequestMapping("guideheader")
	public String noticeheader() {
		return "guide/guideheader";
	}

	@RequestMapping("guidefooter")
	public String noticefooter() {
		return "guide/guidefooter";
	}
	
	@RequestMapping("guide_room")
	public String guide_room(Model model) {
		model.addAttribute("menu","guide_room");
		return "guide/guide_room";
	}
	@RequestMapping("guide_join")
	public String guide_join(Model model) {
		model.addAttribute("menu","guide_join");
		return "guide/guide_join";
	}
	@RequestMapping("guide_book")
	public String guide_book(Model model) {
		model.addAttribute("menu","guide_book");
		return "guide/guide_book";
	}
}
