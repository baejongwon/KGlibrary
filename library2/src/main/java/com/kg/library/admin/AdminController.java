package com.kg.library.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
	@Autowired private HttpSession session;
	@Autowired private AdminService service;

	@RequestMapping("admin")
	public String admin(String search, @RequestParam(value="currentPage", required = false)String cp, String select, Model model){
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		if (!sessionId.equals("admin") || sessionId.trim().isEmpty()) {
			System.out.println(sessionId);
			return "index";
		}
		if(search == null || search.trim().isEmpty()) {
			search = ""; select = "id";
		}
		service.getMember(cp, model, select, search);
		
		
		return "/admin/admin";
	}
	
	@RequestMapping("adminDelete")
	public String adminDelete(String id) {
		String sessionId = (String) session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		if (!sessionId.equals("admin") || sessionId.trim().isEmpty()) {
			System.out.println(sessionId);
			return "index";
		}
		service.adminDelete(id);
		return "redirect:admin";
	}
	
	@RequestMapping("adminHeader")
	public String adminHeader() {
		return "/admin/adminHeader";
	}
	
	@RequestMapping("adminFooter")
	public String adminFooter() {
		return "/admin/adminFooter";
	}
}
