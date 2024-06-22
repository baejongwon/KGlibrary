package com.kg.library.member;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kg.library.Introduction.BookDTO;
import com.kg.library.Introduction.ReservationDTO;

import jakarta.servlet.http.HttpSession;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
public class MemberService {

	@Autowired
	private IMemberMapper mapper;
	@Autowired
	private HttpSession session;
//	@Autowired private ReservationMapper mapper2;
//	@Autowired private IBookMapper mapper3;

	@Value("${coolsms.apikey}")
	private String apiKey;

	@Value("${coolsms.apisecret}")
	private String apiSecret;

	@Value("${coolsms.fromnumber}")
	private String fromNumber;

	int count = 0;

	public String joinProc(MemberDTO member) {
		if (member.getId() == null || member.getId().trim().isEmpty()) {
			return "아이디를 입력하세요.";
		}
		if (member.getPw() == null || member.getPw().trim().isEmpty()) {
			return "비밀번호를 입력하세요.";
		}
		if (member.getPw().equals(member.getConfirm()) == false) {
			return "두 비밀번호를 일치하여 입력하세요.";
		}
		if (member.getName() == null || member.getName().trim().isEmpty()) {
			return "이름을 입력하세요.";
		}

		MemberDTO check = mapper.login(member.getId());
		if (check != null) {
			return "이미 사용중인 아이디 입니다.";
		}

		/* 암호화 과정 */
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String secretPass = encoder.encode(member.getPw());
		member.setPw(secretPass);

		int result = mapper.joinProc(member);
		if (result == 1)
			return "회원 등록 완료";

		return "회원 등록을 다시 시도하세요.";
	}

	public String loginProc(String id, String pw) {
		if (id == null || id.trim().isEmpty()) {
			return "아이디를 입력하세요.";
		}
		if (pw == null || pw.trim().isEmpty()) {
			return "비밀번호를 입력하세요.";
		}

		MemberDTO check = mapper.login(id);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (check != null && encoder.matches(pw, check.getPw()) == true) {
			session.setAttribute("id", check.getId());
			session.setAttribute("name", check.getName());
			session.setAttribute("email", check.getEmail());
			session.setAttribute("address", check.getAddress());
			session.setAttribute("tel", check.getTel());
			return "로그인 성공";
		}

		return "아이디 또는 비밀번호를 확인 후 다시 입력하세요.";
	}

	public void certifiedPhoneNumber(String tel, String numStr) {
		Message coolsms = new Message(apiKey, apiSecret);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("to", tel);
		params.put("from", fromNumber);
		params.put("type", "SMS");
		params.put("text", "[" + numStr + "]");
		params.put("app_version", "test app 1.2"); // application name and version

		System.out.println("CoolSMS API Request Parameters: " + params);

		try {
			JSONObject obj = (JSONObject) coolsms.send(params);
			System.out.println(obj.toString());
		} catch (CoolsmsException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCode());
		}
	}

	public String userInfo(Model model) {
		String sessionId = (String) session.getAttribute("id");
		if (sessionId == null)
			return "로그인 후 이용하세요.";

		MemberDTO member = mapper.login(sessionId);
		if (member.getAddress() != null && member.getAddress().isEmpty() == false) {
			String[] address = member.getAddress().split(",");
			model.addAttribute("postcode", address[0]);
			member.setAddress(address[1]);
			model.addAttribute("detailAddress", address[2]);
		}
		model.addAttribute("member", member);
		return "회원 검색 완료";
	}

	public String updateProc(MemberDTO member) {
		if (member.getPw() == null || member.getPw().trim().isEmpty()) {
			return "비밀번호를 입력하세요.";
		}
		if (member.getPw().equals(member.getConfirm()) == false) {
			return "두 비밀번호를 일치하여 입력하세요.";
		}
		if (member.getName() == null || member.getName().trim().isEmpty()) {
			return "이름을 입력하세요.";
		}
		/* 암호화 과정 */
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String secretPass = encoder.encode(member.getPw());
		member.setPw(secretPass);

		session.setAttribute("id", member.getId());
		session.setAttribute("name", member.getName());
		session.setAttribute("email", member.getEmail());
		session.setAttribute("address", member.getAddress());
		session.setAttribute("tel", member.getTel());

		int result = mapper.updateProc(member);
		if (result == 1)
			return "회원 수정 완료";

		return "회원 수정을 다시 시도하세요.";
	}

	public String deleteProc(MemberDTO member) {
		if (member.getPw() == null || member.getPw().trim().isEmpty()) {
			return "비밀번호를 입력하세요.";
		}
		if (member.getPw().equals(member.getConfirm()) == false) {
			return "두 비밀번호를 일치하여 입력하세요.";
		}

		MemberDTO check = mapper.login(member.getId());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (check != null && encoder.matches(member.getPw(), check.getPw()) == true) {
			int result = mapper.deleteProc(member.getId());
			if (result == 1)
				return "회원 삭제 완료";
			return "회원 삭제를 다시 시도하세요.";
		}

		return "아이디 또는 비밀번호를 확인 후 입력하세요";
	}
	
	public ResponseEntity<List<ReservationDTO>> myReservation(String sessionId,Model model) {
	    // RestTemplate 생성
	    RestTemplate restTemplate = new RestTemplate();
	    restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());

	    // 서버에게 ID를 전송하는 URL
	    String apiUrl = "https://www.bowfun.link/reservation/requestMyReservation";

	    // POST 요청을 위한 헤더 및 본문 설정
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);

	    // JSON 데이터 설정
	    String jsonBody = "{\"sessionId\": \"" + sessionId + "\"}";

	    // 요청 엔터티 생성
	    HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

	    // POST 요청 보내기
	    ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

	    // 응답 확인
	    if (responseEntity.getStatusCode().is2xxSuccessful()) {
	        String responseBody = responseEntity.getBody();

	        try {
	            ObjectMapper objectMapper = new ObjectMapper();

	            // JSON 문자열을 List<ReservationDTO>로 변환
	            List<ReservationDTO> reservationDTOList = objectMapper.readValue(responseBody, new TypeReference<List<ReservationDTO>>() {
	            });
	            
				model.addAttribute("reservations", reservationDTOList);
	            return ResponseEntity.ok(reservationDTOList);

	        } catch (IOException e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    } else {
	        System.out.println("Request failed. Status code: " + responseEntity.getStatusCode());
	        return ResponseEntity.status(responseEntity.getStatusCode()).build();
	    }
	}

	public void cancel(ReservationDTO dto) {
		// RestTemplate 생성
	    RestTemplate restTemplate = new RestTemplate();

	    // 서버에게 ID를 전송하는 URL
	    String apiUrl = "https://www.bowfun.link/reservation/requestCancel";

	    // POST 요청을 위한 헤더 및 본문 설정
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON); // Json 데이터, @RequestBody으로 받을 수 있음.

	    // DTO를 JSON으로 변환
	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonBody;
	    try {
	        jsonBody = objectMapper.writeValueAsString(dto);
	    } catch (JsonProcessingException e) {
	        e.printStackTrace();
	        return; // 예외 처리 필요
	    }

	    // 요청 엔터티 생성
	    HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

	    // POST 요청 보내기
	    ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

	    // 응답 확인
	    if (responseEntity.getStatusCode().is2xxSuccessful()) {
	        String responseBody = responseEntity.getBody();
	        System.out.println("Response body: " + responseBody);

	    } else {
	        System.out.println("Request failed. Status code: " + responseEntity.getStatusCode());
	    }
	}
	
	public ResponseEntity<List<ReservationDTO>> preReservation(String sessionId) {
		// RestTemplate 생성
	    RestTemplate restTemplate = new RestTemplate();
	    restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());

	    // 서버에게 ID를 전송하는 URL
	    String apiUrl = "https://www.bowfun.link/reservation/requestPreReservation";

	    // POST 요청을 위한 헤더 및 본문 설정
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);

	    // JSON 데이터 설정
	    String jsonBody = "{\"sessionId\": \"" + sessionId + "\"}";

	    // 요청 엔터티 생성
	    HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

	    // POST 요청 보내기
	    ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

	    // 응답 확인
	    if (responseEntity.getStatusCode().is2xxSuccessful()) {
	        String responseBody = responseEntity.getBody();

	        try {
	            ObjectMapper objectMapper = new ObjectMapper();

	            // JSON 문자열을 List<ReservationDTO>로 변환
	            List<ReservationDTO> reservationDTOList = objectMapper.readValue(responseBody, new TypeReference<List<ReservationDTO>>() {
	            });

	            return ResponseEntity.ok(reservationDTOList);

	        } catch (IOException e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    } else {
	        System.out.println("Request failed. Status code: " + responseEntity.getStatusCode());
	        return ResponseEntity.status(responseEntity.getStatusCode()).build();
	    }
	}

	public void myBook(Model model, String id) { // ID 보낼 때

		System.out.println("mybook service 보내고 있나1");
		// RestTemplate 생성
		RestTemplate restTemplate = new RestTemplate();

		// 전송할 ID 값
		// String userId = id; // (String)session.getAttribute("id");

		// 서버에게 ID를 전송하는 URL
		String apiUrl = "https://www.bowfun.link/book/requestMyBook";

		// POST 요청을 위한 헤더 및 본문 설정 (어떤 데이터 형식으로 보낼지 결정하는 부분) / 손잡이(재료)
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON); // Json 데이터, @RequestBody으로 받을 수 있음.

		// JSON 데이터 설정 (보낼 값 형식 key,value) / 검날(재료)
		String jsonBody = "{\"id\": \"" + id + "\"}";

		// 요청 엔터티 생성 (header,body 합쳐서 보낼 데이터 덩어리 만드는 것.) / 검(완제품)
		HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

		// POST 요청 보내기 ()
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
		// 응답 데이터를 어떻게 처리 할지 ResponseEntity에 정의 / apiUrl에, 정보(완제품)을, string형식으로(json이라면
		// 이 부분을 커스텀 클래스를 만들어서 그 형식으로 보낼 수 있음), HTTP POST요청을 보낸다.

		// 응답 확인
		if (responseEntity.getStatusCode().is2xxSuccessful()) {
			String responseBody = responseEntity.getBody();
			System.out.println("Response body: " + responseBody);

			try {
				ObjectMapper objectMapper = new ObjectMapper();

				// JSON 문자열을 List<BookDTO>로 변환
				List<BookDTO> bookDTOList = objectMapper.readValue(responseBody, new TypeReference<List<BookDTO>>() {
				});

				// 변환된 객체 리스트를 사용
				for (BookDTO bookDTO : bookDTOList) {
					System.out.println("Title: " + bookDTO.getTitle_info() + ", Author: " + bookDTO.getAuthor_info());
				}

				// 모델에 BookDTO 리스트 추가
				model.addAttribute("boards", bookDTOList);

			} catch (IOException e) {
				e.printStackTrace();
			}
			// 문자열을 BookDTO 객체로 변환

		} else {
			System.out.println("Request failed. Status code: " + responseEntity.getStatusCode());
		}
	}

	public void borrowDateExtend(String no, String sessionId) { // ID 보낼 때

		// RestTemplate 생성
		RestTemplate restTemplate = new RestTemplate();

		// 서버에게 ID를 전송하는 URL
		String apiUrl = "https://www.bowfun.link/book/requestDateExtend";

		// POST 요청을 위한 헤더 및 본문 설정 (어떤 데이터 형식으로 보낼지 결정하는 부분) / 손잡이(재료)
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON); // Json 데이터, @RequestBody으로 받을 수 있음.

		// JSON 데이터 설정 (보낼 값 형식 key,value) / 검날(재료)
		String jsonBody = "{\"id\": \"" + sessionId + "\", \"no\": \"" + no + "\"}";

		// 요청 엔터티 생성 (header,body 합쳐서 보낼 데이터 덩어리 만드는 것.) / 검(완제품)
		HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

		// POST 요청 보내기 ()
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
		// 응답 데이터를 어떻게 처리 할지 ResponseEntity에 정의 / apiUrl에, 정보(완제품)을, string형식으로(json이라면
		// 이 부분을 커스텀 클래스를 만들어서 그 형식으로 보낼 수 있음), HTTP POST요청을 보낸다.

		// 응답 확인
		if (responseEntity.getStatusCode().is2xxSuccessful()) {
			String responseBody = responseEntity.getBody();
			System.out.println("Response body: " + responseBody);

		} else {
			System.out.println("Request failed. Status code: " + responseEntity.getStatusCode());
		}
	}

	public void returnProc2(String no) { // ID 보낼 때

		// RestTemplate 생성
		RestTemplate restTemplate = new RestTemplate();

		// 서버에게 ID를 전송하는 URL
		String apiUrl = "https://www.bowfun.link/book/requestreturnProc2";

		// POST 요청을 위한 헤더 및 본문 설정 (어떤 데이터 형식으로 보낼지 결정하는 부분) / 손잡이(재료)
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON); // Json 데이터, @RequestBody으로 받을 수 있음.

		// JSON 데이터 설정 (보낼 값 형식 key,value) / 검날(재료)
		String jsonBody = "{\"no\": \"" + no + "\"}";

		// 요청 엔터티 생성 (header,body 합쳐서 보낼 데이터 덩어리 만드는 것.) / 검(완제품)
		HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

		// POST 요청 보내기 ()
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
		// 응답 데이터를 어떻게 처리 할지 ResponseEntity에 정의 / apiUrl에, 정보(완제품)을, string형식으로(json이라면
		// 이 부분을 커스텀 클래스를 만들어서 그 형식으로 보낼 수 있음), HTTP POST요청을 보낸다.

		// 응답 확인
		if (responseEntity.getStatusCode().is2xxSuccessful()) {
			String responseBody = responseEntity.getBody();
			System.out.println("Response body: " + responseBody);

		} else {
			System.out.println("Request failed. Status code: " + responseEntity.getStatusCode());
		}
	}

	public String nameTelCheck(MemberDTO member) {
		MemberDTO result = mapper.findId(member.getName(), member.getTel());
		if (result == null) {
			return "error";
		} else {
			return "success";
		}
	}

	public void findIdResult(Model model, MemberDTO member) {
		String name = member.getName();
		String tel = member.getTel();
		String id = mapper.findIdResult(name, tel);
		System.out.println(name + tel + id);
		model.addAttribute("id", id);
	}

	public String idTelCheck(MemberDTO member) {
		MemberDTO result = mapper.findPw(member.getId(), member.getTel());
		if (result == null) {
			return "error";
		} else {
			return "success";
		}
	}

	public void findPwResult(Model model, MemberDTO member, String numStr) {
		String id = member.getId();
		String tel = member.getTel();

		System.out.println(id + tel + numStr);
		model.addAttribute("pw", numStr);
		/* 암호화 과정 */
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String secretPass = encoder.encode(numStr);
		member.setPw(secretPass);

		mapper.findPwResult(id, tel, secretPass);
	}
}
