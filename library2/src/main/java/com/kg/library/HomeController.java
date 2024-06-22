
package com.kg.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import com.kg.library.Introduction.BookService;
import com.kg.library.culture.CultureService;
import com.kg.library.notice.NoticeBoardService;

@Controller
public class HomeController {
	@Autowired
	private NoticeBoardService notice_service;
//	@Autowired
//	private BookService Book_Service;
	@Autowired
	private CultureService clture_Service;
	@Autowired
	private MainService main_Service;
	
	@RequestMapping("index")
	public void index() {}
	
	@RequestMapping("header")
	public String header() {
		return "default/header";
	}	
	@RequestMapping("main")
	public String main(Model model) {
		notice_service.main_board(model);
		clture_Service.main_board(model);
		main_Service.hit_book(model);
		main_Service.new_book(model);
		return "default/main";
	}
	@RequestMapping("footer")
	public String footer() {
		return "default/footer";
	}
	
//	@RequestMapping("bookForm")
//	public String bookForm(String search,Model model,
//			@RequestParam(value="currentPage", required = false)String cp, String select) {
//		String path = main_Service.search(cp, model, search, select);
//		return path;
//	}
}
