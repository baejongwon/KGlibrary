package com.kg.library.culture;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import jakarta.servlet.http.HttpSession;

@Controller
public class RegistController {

	@Autowired
	private HttpSession session;
	@Autowired
	RegistService service;
	
	@Autowired
	CultureService C_service;

		
		@RequestMapping("registWrite")
		public String registWrite(String no, Model model) {
			String sessionId = (String) session.getAttribute("id");
			if(sessionId == null) {
				return "redirect:login";
			}
			
			
			CultureDTO board = C_service.cultureContent(no,model);
			String title = board.getTitle();

			String count = service.registCount(title,sessionId);
			
			System.out.println("count : "+ count);
			if("0".equals(count)) {
				service.registWrite(no, model);
				return "culture/registWrite";
			}else {
				System.out.println("이미 글이 있습니다.");
				return "redirect:cultureboard";	
			}
		}
		
		@RequestMapping("registWritePro")
		public String registWritePro(
				@RequestParam("no") String no,
				@RequestParam("title") String title,
				@RequestParam("name") String name,
				@RequestParam("p_name") String p_name,
				@RequestParam("tel") String tel,
				@RequestParam("email") String email,
				@RequestParam("address") String address,
				@RequestParam("es_school") String es_school,
				@RequestParam("es_hak") String es_hak,
				@RequestParam("es_ban") String es_ban ,
				@RequestParam("contents") String contents) {
			
			String Path = service.registWritePro(no,title,name,p_name,tel,email,address,es_school,es_hak,es_ban,contents);
			
			return Path;
		}
		
		@RequestMapping("registboard")
		public String registboard(String no, Model model) {
			String sessionId = (String) session.getAttribute("id");
			
			if(sessionId == null) {
				return "redirect:login";
			}
			
			RegistDTO board = service.registboard(no,sessionId);
			model.addAttribute("menu", "cultureboard");
			model.addAttribute("board", board);
			model.addAttribute("no",no);
			return "culture/registboard";
		}
		
		@RequestMapping("registDelete")
		public String registDelete(String no) {
			String sessionId = (String) session.getAttribute("id");
			
			if(sessionId == null) {
				return "redirect:login";
			}
			
			service.registDelete(no,sessionId);
			return "redirect:cultureboard";
		}
	
}
