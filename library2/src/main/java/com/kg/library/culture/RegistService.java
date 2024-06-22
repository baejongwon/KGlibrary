package com.kg.library.culture;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kg.library.PageService;
import com.kg.library.member.MemberDTO;

import jakarta.servlet.http.HttpSession;

@Service
public class RegistService {

	@Autowired
	private CultureMapper mapper;
	@Autowired
	private HttpSession session;
	private String filePath = "C:\\Users\\user1\\git\\kglibrary\\library\\src\\main\\resources\\static\\img\\";
	
	
	public void registWrite(String no, Model model) {
		String sessionId = (String)session.getAttribute("id");
		
		int n = 0;
		try {
			n = Integer.parseInt(no);
		}catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		CultureDTO C_board = mapper.cultureContent(n);
		MemberDTO M_board = mapper.login(sessionId);
		
		String title = C_board.getTitle();
		model.addAttribute("title",title);
		model.addAttribute("M_board",M_board);
	}


	public String registWritePro(String no,String title, String name, String p_name, String tel, String email, String address, String es_school, String es_hak, String es_ban, String contents) {
		String sessionId = (String) session.getAttribute("id");
		if (sessionId == null)
			return "redirect:cultureboard";
		
		RegistDTO R_board = new RegistDTO();
		R_board.setTitle(title);
		R_board.setId(sessionId);
		R_board.setName(name);
		R_board.setP_name(p_name);
		R_board.setTel(tel);
		R_board.setEmail(email);
		R_board.setAddress(address);
		R_board.setEs_school(es_school);
		R_board.setEs_hak(es_hak);
		R_board.setEs_ban(es_ban);
		contents = contents.replace("\r\n","<br>");
		R_board.setContents(contents);
		
		mapper.registWritePro(R_board);
		
		int n =0;
		try {
			n=Integer.parseInt(no);
		}catch (Exception e) {
			e.printStackTrace();
		}
		CultureDTO C_board = mapper.cultureContent(n);
//		String applicant = mapper.applicants_count(C_board);
//		int app_count = Integer.parseInt(applicant);
//		
//		app_count++;
//		String newApplicant = String.valueOf(app_count);
//		C_board.setApplicants(newApplicant);
		
//		mapper.updateApplicantsCount(newApplicant,n);
		
		return "redirect:cultureboard";
	}


	public String registCount(String title, String sessionId) {
		String result = mapper.registCount(title,sessionId);
		return result;
	}


	public RegistDTO registContent(String no) {
		int n=0;
		n=Integer.parseInt(no);
		RegistDTO board = mapper.registContent(n);

		return board;
	}


	public RegistDTO registboard(String no, String sessionId) {
		
		int n = 1;
		try {
			n = Integer.parseInt(no);
		}catch (Exception e) {
			return null;
		}
		
		CultureDTO C_board = mapper.cultureContent(n);
		String title = C_board.getTitle();
		
		RegistDTO board = mapper.registboard(title,sessionId);
		
		
		return board;
	}


	public void registDelete(String no,String sessionId) {
		int n = 1;
		try {
			n = Integer.parseInt(no);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		CultureDTO C_board = mapper.cultureContent(n);
		String title = C_board.getTitle();
		
		mapper.registDelete(title,sessionId);
		return;
	}

}
