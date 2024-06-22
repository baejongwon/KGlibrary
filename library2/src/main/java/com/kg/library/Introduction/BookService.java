package com.kg.library.Introduction;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kg.library.PageService;

import jakarta.servlet.http.HttpSession;

@Service
public class BookService {
	//private String filePath = "C:\\Users\\user1\\git\\kglibrary\\library\\src\\main\\resources\\static\\img\\";
	private String filePath = "C:\\Users\\User\\git\\kglibrary\\library\\src\\main\\resources\\static\\img\\";
	private static final Logger logger = LoggerFactory.getLogger(BookService.class);
	@Autowired
	IBookMapper mapper;
	@Autowired
	private HttpSession session;

	public void bookForm(String cp, Model model, String search, String select) { // 책 검색 게시판

		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(cp);
		} catch (Exception e) {
			currentPage = 1;
		}

		int pageBlock = 10; // 한 페이지에 보일 데이터의 수
		int end = pageBlock * currentPage; // 테이블에서 가져올 마지막 행번호
		int begin = end - pageBlock + 1; // 테이블에서 가져올 시작 행번호

		List<BookDTO> boards = mapper.bookForm(begin, end, select, search); // 도서 리스트
		int totalCount = mapper.totalCount(select, search); // 도서 리스트가 0인지 아닌지
		if (totalCount == 0) { // db에 리스트 정보가 없으면(0) 되돌아 감.
			return;
		}

		String url = "bookForm?select=" + select + "&search=" + search + "&currentPage=";
		String result = PageService.printPage(url, totalCount, pageBlock, currentPage);

		model.addAttribute("count", totalCount);
		model.addAttribute("boards", boards);
		model.addAttribute("result", result);
	}

	public String bookRegistProc(MultipartHttpServletRequest multi) { // 책 등록

			String sessionId = (String) session.getAttribute("id");
			if (sessionId == null)
				return "redirect:login";
	
			
			LocalDateTime currentTime = LocalDateTime.now(); // 현재 시간 가져오기
			LocalDateTime plusTime = currentTime.plus(Duration.ofDays(7));
			Timestamp borrowtime = Timestamp.valueOf(currentTime); //형변환
			Timestamp returntime = Timestamp.valueOf(plusTime); //형변환
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			String rentaldate = plusTime.format(formatter);
			
			int book_count = Integer.parseInt(multi.getParameter("book_count")); //책 갯수
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			
			String fullPath = "";
			
			if(multi.getParameter("category").equals("API")) //API에서 받아온 이미지 라면
			{
				fullPath = multi.getParameter("image");
			}
			else { //사용자가 직접 올린 이미지라면
			MultipartFile file = multi.getFile("upfile");
			if (file.getSize() != 0) { // 클라이언트라 파일을 업로드 했다면
				// 파일 이름
				sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
				String fileTime = sdf.format(new Date());
				String fileName = file.getOriginalFilename();

				String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
				System.out.println("bookRegist-suffix : " + suffix);
				if (suffix.equalsIgnoreCase("jpg") == false)
					return "redirect:bookRegist";

				// 파일 저장 경로
				String fileSaveDirectory = filePath + sessionId;
				File f = new File(fileSaveDirectory);
				if (f.exists() == false) {// 파일이 없다면
					f.mkdirs(); // 폴더 생성
				}
				System.out.println(fileSaveDirectory);
				// String suffix = fileName.substring(beginIndex)

				fullPath = fileSaveDirectory + "\\" + fileTime + fileName;
				System.out.println("이미지 경로 " + fullPath);
				// board.setImage(fullPath);

				f = new File(fullPath);
				try {
					file.transferTo(f); // 파일 저장
				} catch (Exception e) {
					e.printStackTrace();
					fullPath = ""; // board.setImage("");
				}
				/*
				 * file.transferTo(); 파일을 이동시키는 기능 <input type="file" name= "upfile"을 사용하여 서버에
				 * 파일 데이터가 전달되면 웹서버가 임시파일로 저장을 함. 임시파일로 저장된 파일을 개발자가 원하는 대로 이동 시킬 때 사용함.
				 * 
				 */
			}
		}
		sdf = new SimpleDateFormat("yyyy-MM-dd");

		for (int i = 1; i <= book_count; i++) {
			BookDTO board = new BookDTO();

			board.setCategory(multi.getParameter("category")); // 카테고리
			board.setTitle_info(multi.getParameter("title_info")); // 제목
			board.setAuthor_info(multi.getParameter("author_info")); // 저작자

			String pub_info = multi.getParameter("pub_info");
			if (pub_info == null || pub_info.trim().isEmpty()) { // 발행자가 없을 때 저작자와 동일
				board.setPub_info(multi.getParameter("author_info"));
			} else { // 발행자
				board.setPub_info(pub_info);
			}

			board.setPub_year_info(multi.getParameter("pub_year_info")); // 발행연도
			board.setReg_date(sdf.format(new Date())); // 비치일(등록 시간)

			String contents = multi.getParameter("detail_link"); // testarea enter값 변경 후 저장
			if (contents == null || contents.trim().isEmpty()) {
				board.setDetail_link("상세 정보 없음.");
			} else {
				contents = contents.replace("\r\n", "<br>"); // 수정을 하겠다면 그부분 다시 relpace 해줘야함
				board.setDetail_link(contents); // 상세정보 , 페이지 경로
			}
			board.setImage(fullPath);
			board.setBorrowperson("대여 가능"); // 빌린 사람
			board.setBook_count(i); // 동일한 책 번호(책 갯수)
			board.setBorrowdate(borrowtime);
			board.setRentaldate(rentaldate);
			
			String donation = multi.getParameter("donation"); 
			if(donation == null || donation.trim().isEmpty()) { // 기증자가 없을 시
			board.setDonation("없음"); }
			else {
			board.setDonation(donation); }
			
			board.setHitbook(multi.getParameter("hitbook"));
			
			

			System.out.println(board.getCategory());
			System.out.println(board.getTitle_info());
			System.out.println(board.getAuthor_info());
			System.out.println(board.getPub_info());
			System.out.println(board.getPub_year_info());
			System.out.println(board.getReg_date());
			System.out.println(board.getDetail_link());
			System.out.println(board.getImage());
			System.out.println(board.getBorrowperson());
			System.out.println(board.getBook_count());
			System.out.println(board.getDonation());

			mapper.bookRegistProc(board);
		}

		return "redirect:bookForm";
	

	}

	public BookDTO bookContent(String no, Model model) { // 책 상세정보

		int n = 1;
		try {
			n = Integer.parseInt(no);
		} catch (Exception e) {
			return null;
		}

		BookDTO board = mapper.bookContent(n);

		if (board != null) {
			System.out.println("image name = " + board.getImage());

			if (board.getImage() != null && !board.getCategory().equals("API")) { // API에서 받은 이미지가 아니면
				String[] names = board.getImage().split("\\\\");

				for (String name : names)
					System.out.println("BoardService-boardContent name : " + name);
				String[] fileNames = names[12].split("-", 2);
				for (String fileName : fileNames)
					System.out.println("BoardService-boardContent fileName : " + fileName);

				board.setImage(names[12]);
			}
		}

		// 7일 후의 시간 계산
		LocalDateTime storedTime = board.getBorrowdate().toLocalDateTime(); // DB 값 형 변환
		LocalDateTime rentalday = storedTime.plus(Duration.ofDays(7)); // 대여 종료 시점

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // 보이기용
		String rentaldate = rentalday.format(formatter);// 보이기용 형변환

		model.addAttribute("rentaldate", rentaldate);
		return board;
	}

	public void rentalProc(String no, String sessionId) { // 대여

		int n = 1;
		
		try{
			n = Integer.parseInt(no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LocalDateTime currentTime = LocalDateTime.now(); //대여 시간, 현재 시간 가져오기
		LocalDateTime plusTime = currentTime.plus(Duration.ofDays(7)); //대여 시간
		
		Timestamp borrowtime = Timestamp.valueOf(currentTime); //형변환
		Timestamp returntime = Timestamp.valueOf(plusTime); //형변환
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String rentaldate = plusTime.format(formatter);
		
		mapper.rentalProc(n, sessionId, borrowtime, rentaldate);
		
	}

	public void returnProc(String no) { // 반납

		int n = 1;
		try {
			n = Integer.parseInt(no);
		} catch (Exception e) {
		}

		String borrowperson = "대여 가능";

		mapper.returnProc(n, borrowperson);

	}

	public void bookDeleteProc(String no) {
		int n = 1;
		try {
			n = Integer.parseInt(no);
		} catch (Exception e) {
		}

		mapper.bookDeleteProc(n);
	}

	private String accessToken;

	public void apiRegistProc(String pageNum, String select, String search, Model model) { // Open Api 받아 오는 곳

		String apimessage = "none";
		try {
			String baseUrl = "https://www.nl.go.kr/NL/search/openApi/search.do";
			String apiKey = "ed1b7493daabfd52d2b4315bf7a15020761ac4ba059345f9bcc932308d6d0ffb";
			String category = "도서";
			String keyword = search;
			int pageSize = 10;

			String encodedUrl = "";
			// 각 부분을 따로 인코딩하고 URL을 조합

			encodedUrl = baseUrl + "?key=" + URLEncoder.encode(apiKey, "UTF-8") + "&kwd="
					+ URLEncoder.encode(keyword, "UTF-8") + "&category=" + URLEncoder.encode(category, "UTF-8")
					+ "&pageSize=" + pageSize + "&pageNum=" + pageNum;

			URL url = new URL(encodedUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");

			XmlMapper xmlMapper = new XmlMapper();

			JsonNode jsonNode = xmlMapper.readTree(conn.getInputStream()); // xml 파일 받아 JsonNode로 형변환
			System.out.println("XML 확인: " + jsonNode);

			// result 노드 가져오기
			JsonNode resultNode = jsonNode.get("result");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			LocalDateTime currentTime = LocalDateTime.now(); // 현재 시간 가져오기
			Timestamp borrowtime = Timestamp.valueOf(currentTime); // 형변환

			List<BookDTO> bookList = new ArrayList<BookDTO>();
			JsonNode itemArray = resultNode.path("item");

			// XML 확인: {"paramData":
			// {"kwd":"소설","category":"","pageNum":"1","pageSize":"10","sort":"","total":"296104"},
			// "result":{"item":[{"title_info":"소설","type_name":"도서","place_info":"2층
			// 문학실(자료실내비치)","author_info":"은운 지음 ;김장환
			// 옮김","pub_info":"지식을만드는지식","menu_name":"오프라인자료","media_name":"인쇄자료(책자형)","manage_name":"본관","pub_year_info":"2022","control_no":"KMO202206110","doc_yn":"LD_VIEWER","org_link":"이용불가","id":"821804030","place_code":"","type_code":"B1","lic_yn":"L","lic_text":"[국립중앙도서관]-무료","reg_date":"20220304","image_url":"http://cover.nl.go.kr/","detail_link":"/NL/contents/search.do#viewKey=821804030&viewType=AH1","isbn":"9791128859731","call_no":"823.4-22-1","kdc_code_1s":"8","kdc_name_1s":"문학","class_no":"823.4","type_of_res":""},{"title_info":"소설","type_name":"도서","place_info":"디지털도서관
			// 디지털자료실","author_info":"박승극[1909-] 박승극문학전집편집위원회","pub_info":"서울 : 학민사,
			// 2001","menu_name":"온라인자료","media_name":"","manage_name":"디지털도서관","pub_year_info":"2001","control_no":"KMO200115310","doc_yn":"NL_VIEWER","org_link":"이용불가","id":"0000000102971073","place_code":"","type_code":"D1","lic_yn":"S","lic_text":"[국립중앙도서관,협약도서관]-열람,인쇄시
			// 과금","reg_date":"20160121","image_url":"http://cover.nl.go.kr/kolis_on/2015/CNTS-00076771188_thumbnail.jpg","detail_link":"/NL/contents/search.do#viewKey=CNTS-00076771188&viewType=C","isbn":"8971931248(1)","call_no":"","kdc_code_1s":"8","kdc_name_1s":"문학","class_no":"","type_of_res":""},{"title_info":"소설","type_name":"도서","place_info":"어린이청소년도서관(역삼동)
			// 연구자료실(서고자료, 3층)","author_info":"글: 나도향,김유정 외 ;그림: 플러그","pub_info":"성우 :
			// 성우주니어","menu_name":"오프라인자료","media_name":"인쇄자료(책자형)","manage_name":"어린이청소년도서관","pub_year_info":"[2019]","control_no":"KJU201903156","doc_yn":"N","org_link":"","id":"575865958","place_code":"","type_code":"B1","lic_yn":"N","lic_text":"[관외이용]-무료","reg_date":"20190522","image_url":"http://cover.nl.go.kr/","detail_link":"/NL/contents/search.do#viewKey=575865958&viewType=AH1","isbn":"9788958856719
			// 9788958857099
			// 9788958857105","call_no":"아710-19-14-38-39","kdc_code_1s":"7","kdc_name_1s":"어학","class_no":"710","type_of_res":""},{"title_info":"소설","type_name":"도서","place_info":"4층
			// 도서자료실(서고자료대출반납)","author_info":"5월문학총서간행위원회
			// 엮음","pub_info":"문학들","menu_name":"오프라인자료","media_name":"인쇄자료(책자형)","manage_name":"본관","pub_year_info":"2012","control_no":"KMO201243421","doc_yn":"N","org_link":"","id":"154923099","place_code":"","type_code":"B1","lic_yn":"N","lic_text":"[관외이용]-무료","reg_date":"20121015","image_url":"http://cover.nl.go.kr/kolis/2012/KMO201243421_thumbnail.jpg","detail_link":"/NL/contents/search.do#viewKey=154923099&viewType=AH1","isbn":"9788992680028
			// 9788992680646","call_no":"813.62-12-165","kdc_code_1s":"8","kdc_name_1s":"문학","class_no":"813.62","type_of_res":""},{"title_info":"소설","type_name":"도서","place_info":"4층
			// 도서자료실(서고자료대출반납)","author_info":"은운 지음 ;김장환
			// 옮김","pub_info":"지식을만드는지식","menu_name":"오프라인자료","media_name":"인쇄자료(책자형)","manage_name":"본관","pub_year_info":"2009","control_no":"KMO200936845","doc_yn":"NL_VIEWER","org_link":"이용불가","id":"101728064","place_code":"","type_code":"B1","lic_yn":"S","lic_text":"[국립중앙도서관,협약도서관]-열람,인쇄시
			// 과금","reg_date":"20091006","image_url":"http://cover.nl.go.kr/","detail_link":"/NL/contents/search.do#viewKey=101728064&viewType=AH1","isbn":"9788964062081","call_no":"823.4-9-4","kdc_code_1s":"8","kdc_name_1s":"문학","class_no":"823.4","type_of_res":""},{"title_info":"소설","type_name":"도서","place_info":"4층
			// 도서자료실(서고자료대출반납)","author_info":"제임스 미치너 지음;윤희기
			// 옮김","pub_info":"열린책들","menu_name":"오프라인자료","media_name":"인쇄자료(책자형)","manage_name":"본관","pub_year_info":"2003","control_no":"KMO200325057","doc_yn":"N","org_link":"","id":"28995756","place_code":"","type_code":"B1","lic_yn":"N","lic_text":"[관외이용]-무료","reg_date":"20030918","image_url":"http://cover.nl.go.kr/","detail_link":"/NL/contents/search.do#viewKey=28995756&viewType=AH1","isbn":"8932905061","call_no":"843-3-406","kdc_code_1s":"8","kdc_name_1s":"문학","class_no":"843","type_of_res":""},{"title_info":"소설","type_name":"도서","place_info":"디지털도서관
			// 디지털자료실","author_info":"제임스 A. 미치너 저;윤희기
			// 옮김","pub_info":"열린책들","menu_name":"오프라인자료","media_name":"인쇄자료(책자형)","manage_name":"본관","pub_year_info":"1992","control_no":"KMO199201056","doc_yn":"NL_VIEWER","org_link":"이용불가","id":"231037","place_code":"","type_code":"B1","lic_yn":"S","lic_text":"[국립중앙도서관,협약도서관]-열람,인쇄시
			// 과금","reg_date":"19960822","image_url":"http://cover.nl.go.kr/","detail_link":"/NL/contents/search.do#viewKey=231037&viewType=AH1","isbn":"8932900027","call_no":"843-미854ㅅ윤","kdc_code_1s":"8","kdc_name_1s":"문학","class_no":"843","type_of_res":""},{"title_info":"[소설]","type_name":"고문헌","place_info":"고문헌실(5층)","author_info":"[著者未詳]","pub_info":"[刊寫字未詳]","menu_name":"오프라인자료","media_name":"기타
			// 비도서자료","manage_name":"본관","pub_year_info":"[刊寫年未詳]","control_no":"KOL000048968","doc_yn":"N","org_link":"","id":"5255620","place_code":"","type_code":"B2","lic_yn":"N","lic_text":"[관외이용]-무료","reg_date":"20040419","image_url":"http://cover.nl.go.kr/","detail_link":"/NL/contents/search.do#viewKey=5255620&viewType=AH1","isbn":"","call_no":"古3636-153","kdc_code_1s":"7","kdc_name_1s":"어학","class_no":"3636","type_of_res":""},{"title_info":"소설","type_name":"도서","place_info":"디지털도서관
			// 디지털자료실","author_info":"은운[471-529] 지음 김장환[1963-] 옮김","pub_info":"서울 :
			// 지식을만드는지식,
			// 20220128","menu_name":"온라인자료","media_name":"","manage_name":"디지털도서관","pub_year_info":"20220128","control_no":"KMO202206110","doc_yn":"LD_VIEWER","org_link":"이용불가","id":"0000000138505468","place_code":"","type_code":"D1","lic_yn":"L","lic_text":"[국립중앙도서관]-무료","reg_date":"20231222","image_url":"http://cover.nl.go.kr/","detail_link":"/NL/contents/search.do#viewKey=CNTS-00127639143&viewType=C","isbn":"9791128859755","call_no":"","kdc_code_1s":"8","kdc_name_1s":"문학","class_no":"","type_of_res":""},{"title_info":"소설","type_name":"음악자료","place_info":"디지털도서관
			// 디지털자료실","author_info":"최봉구[1958-] 노래·작사 김병걸[1958-] 작곡 남기연 편곡","pub_info":"서울
			// : YK엔터테인먼트 [제작] : 한국음반산업협회 [배급], 20191130
			// (20191130)","menu_name":"온라인자료","media_name":"","manage_name":"디지털도서관","pub_year_info":"20191130","control_no":"KMU202001793","doc_yn":"NL_VIEWER","org_link":"이용불가","id":"0000000131131985","place_code":"","type_code":"D5","lic_yn":"L","lic_text":"[국립중앙도서관]-무료","reg_date":"20201203","image_url":"http://cover.nl.go.kr/","detail_link":"/NL/contents/search.do#viewKey=CNTS-00120182960&viewType=C","isbn":"","call_no":"","kdc_code_1s":"6","kdc_name_1s":"예술","class_no":"","type_of_res":""}]}}

			System.out.println("Debug: resultNode - " + resultNode);
			System.out.println("Debug: itemArray - " + itemArray);

			System.out.println("size = " + itemArray.size());
			if (itemArray.size() == 0) {
				apimessage = "not_search";
			} else {
				for (int i = 0; i < itemArray.size(); i++) {

					JsonNode bookNode = itemArray.get(i); // 한번 가공해야 함.
					BookDTO bookDTO = new BookDTO();

					bookDTO.setCategory("API");
					bookDTO.setTitle_info(bookNode.path("title_info").asText()); // 제목
					bookDTO.setAuthor_info(bookNode.path("author_info").asText()); // 저작자
					bookDTO.setPub_info(bookNode.path("pub_info").asText()); // 발행자
					bookDTO.setPub_year_info(bookNode.path("pub_year_info").asText()); // 발행일
					bookDTO.setReg_date(sdf.format(new Date())); // 등록 시간
					bookDTO.setDetail_link(bookNode.path("detail_link").asText()); // 상세 정보
					bookDTO.setImage(bookNode.path("image_url").asText()); //이미지 url
					bookDTO.setBorrowperson("대여 가능"); // 대여구분, 대여자
					bookDTO.setBook_count(1);	// 책 갯수 고정
					bookDTO.setDonation(" ");	// 기부자 없음
					bookDTO.setBorrowdate(borrowtime); //대여 시작 시간 기본값
					bookDTO.setRentaldate(sdf.format(new Date())); //대여 끝나는 시간 기본값
					bookDTO.setHitbook(" "); //추천 도서
					bookList.add(bookDTO);
				
					mapper.bookRegistProc(bookDTO);

				}
				apimessage = "search";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("apimessage", apimessage);
	}


	
/*	
	public BookDTO bookdata() {
		
		int n = 1;

		BookDTO board = mapper.bookContent(n);

		if (board != null) {
			System.out.println("image name = " + board.getImage());

			if (board.getImage() != null && !board.getCategory().equals("API")) { // API에서 받은 이미지가 아니면
				String[] names = board.getImage().split("\\\\");

				for (String name : names)
					System.out.println("BoardService-boardContent name : " + name);
				String[] fileNames = names[12].split("-", 2);
				for (String fileName : fileNames)
					System.out.println("BoardService-boardContent fileName : " + fileName);

				board.setImage(names[12]);
			}
		}

		return board;
		*/
	
	
	//메인화면 추천 도서
//	public void hit_book(Model model) {
//		
//		ArrayList<BookDTO> hitbooks = mapper.hitbooks();
//		if(hitbooks!=null) {
//			for(BookDTO b:hitbooks) {
//				if(b.getImage()==null || b.getImage().trim().isEmpty()) {
//					b.setImage("20240109150111-40641325628.20230718121618.jpg");
//					continue;
//				}
//				String[] names = b.getImage().split("\\\\");
//				//String[] names= b.getImage().split("/");
//				for(String name:names) {
//					System.out.println("name: " +name);
//				}
//				//String[] fileNames= names[1].split("-",2); 
//				String[] fileNames = names[12].split("-", 2);
//				for(String fileName:fileNames) {
//					System.out.println("fileName: " +fileName);
//				}
//				b.setImage(names[12]);
//			}
//		}
//		model.addAttribute("hitbooks", hitbooks);
//	}
//
//	public void new_book(Model model) {
//		ArrayList<BookDTO> newbooks = mapper.newbooks();
//		if(newbooks!=null) {
//			for(BookDTO b:newbooks) {
//				if(b.getImage()==null || b.getImage().trim().isEmpty()) {
//					b.setImage("20240109150111-40641325628.20230718121618.jpg");
//					continue;
//				}
//				if(b.getCategory().equals("API")) //API에서 받아온 이미지 라면
//				{
//					continue;
//				}
//				String[] names = b.getImage().split("\\\\");
//				//String[] names= b.getImage().split("/");
//				for(String name:names) {
//					System.out.println("name: " +name);
//				}
//				//String[] fileNames= names[1].split("-",2); 
//				String[] fileNames = names[12].split("-", 2);
//				for(String fileName:fileNames) {
//					System.out.println("fileName: " +fileName);
//				}
//				b.setImage(names[12]);
//			}
//		}
//		model.addAttribute("newbooks", newbooks);
//
//		
//	}
	
public void hit_book(Model model) {
		
	    try {
            ResponseEntity<ArrayList<BookDTO>> responseEntity = new RestTemplate().exchange(
                    "/book/hit_book",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ArrayList<BookDTO>>() {}
            );

            ArrayList<BookDTO> hitbooks = responseEntity.getBody();
       
    		if(hitbooks!=null) {
			for(BookDTO b:hitbooks) {
				if(b.getImage()==null || b.getImage().trim().isEmpty()) {
					b.setImage("20240109150111-40641325628.20230718121618.jpg");
					continue;
				}
			}
		}    
            // 모델에 데이터 추가
            model.addAttribute("hitbooks", hitbooks);
            
        } catch (HttpClientErrorException.NotFound notFoundException) {
            // 404 Not Found 에러 처리
            logger.error("Server returned 404 Not Found");
            // 다른 예외에 대한 처리도 추가 가능
        } catch (Exception e) {
            // 기타 예외 처리
            logger.error("An error occurred while fetching data from the server", e);
        }
    
	}
public void new_book(Model model) {
	
    try {
        ResponseEntity<ArrayList<BookDTO>> responseEntity = new RestTemplate().exchange(
                "/book/new_Book",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ArrayList<BookDTO>>() {}
        );

        ArrayList<BookDTO> newBooks = responseEntity.getBody();
   
		if(newBooks!=null) {
		for(BookDTO b:newBooks) {
				System.out.print("\u001B[31m");
				System.out.println("8086newbook Category: " + b.getCategory());
                System.out.println("8086newbook Image: " + b.getImage());
                System.out.println("8086newbook Title Info: " + b.getTitle_info());
                System.out.println("8086newbook Author Info: " + b.getAuthor_info());
                System.out.print("\u001B[0m");
			if(b.getImage()==null || b.getImage().trim().isEmpty()) {
				b.setImage("20240109150111-40641325628.20230718121618.jpg");
				continue;
			}
			if(b.getCategory().equals("API")) //API에서 받아온 이미지 라면
			{
				continue;
			}
		}
	}    
        // 모델에 데이터 추가
        model.addAttribute("newBooks", newBooks);
        
    } catch (HttpClientErrorException.NotFound notFoundException) {
        // 404 Not Found 에러 처리
        logger.error("Server returned 404 Not Found");
        // 다른 예외에 대한 처리도 추가 가능
    } catch (Exception e) {
        // 기타 예외 처리
        logger.error("An error occurred while fetching data from the server", e);
    }

}

}
