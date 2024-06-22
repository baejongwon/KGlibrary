package com.kg.library.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.kg.library.PageService;
//import com.kg.library.book.BookDTO;
import com.kg.library.member.IMemberMapper;
import com.kg.library.member.MemberDTO;

@Service
public class AdminService {
	@Autowired IMemberMapper mapper;

	public void getMember(String cp, Model model, String select, String search) {
		int currentPage = 1;
		try{
			currentPage = Integer.parseInt(cp);
		}catch(Exception e){
			currentPage = 1;
		}
		
		int pageBlock = 10; // 한 페이지에 보일 데이터의 수 
		int end = pageBlock * currentPage; // 테이블에서 가져올 마지막 행번호
		int begin = end - pageBlock + 1; // 테이블에서 가져올 시작 행번호
		

		List<MemberDTO> members = mapper.memberInfo(begin, end, select, search); //도서 리스트
		int totalCount = mapper.totalCount(select, search); //도서 리스트가 0인지 아닌지
		if(totalCount == 0) { // db에 리스트 정보가 없으면(0) 되돌아 감.
			return ;
		}
		String url;
		if(search == "") {
			url = "admin?currentPage=";
		}else {
			url = "admin?select="+select+"&search="+search+"&currentPage=";
		}
		String result = PageService.printPage(url, totalCount, pageBlock, currentPage);
		

		model.addAttribute("count", totalCount);
		model.addAttribute("members", members);
		model.addAttribute("result", result);
	}

	public void adminDelete(String id) {
		mapper.deleteProc(id);		
	}

}
