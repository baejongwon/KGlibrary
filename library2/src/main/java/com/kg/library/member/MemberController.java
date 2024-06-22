package com.kg.library.member;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kg.library.Introduction.ReservationDTO;

//import com.kg.library.reservation.ReservationDTO;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	@Autowired private MemberService service;
	@Autowired private HttpSession session;	
	
	@RequestMapping("join")
	public String join(Model model) {
		model.addAttribute("menu","regist");
		return "member/join";
	}
	
	@PostMapping("joinProc")
	public String joinProc(MemberDTO member, String postcode, String detailAddress, Model model, RedirectAttributes ra) {
		if(member.getAddress() != null && member.getAddress().trim().isEmpty() == false)
			member.setAddress( postcode + "," + member.getAddress() + "," + detailAddress);
		
		String msg = service.joinProc(member);
		
		if(msg.equals("회원 등록 완료")) {
			ra.addFlashAttribute("msg", msg);
			return "redirect:index";
		}
		
		model.addAttribute("msg", msg);
		return "member/join";
	}
	
	@RequestMapping("login")
	public String login(Model model) {
		model.addAttribute("menu","login");
		return "member/login";
	}
	
	@PostMapping("loginProc")
	public String loginProc(String id, String pw, Model model, RedirectAttributes ra) {
		String msg = service.loginProc(id, pw);
		if(msg.equals("로그인 성공")) {
			ra.addFlashAttribute("msg", msg);
			return "redirect:index";
		}
		model.addAttribute("msg", msg);
		return "member/login";
	}

	@RequestMapping("logout")
	public String logout(RedirectAttributes ra) {
//		if(session.getAttribute("kakao") != null) {
//			kakaoService.unlink();
//		}
		session.invalidate();
		ra.addFlashAttribute("msg", "로그 아웃");
		return "redirect:index";
	}
	
//	@RequestMapping("kakaoLogin")
//	public String kakaoLogin(String code) {
//		System.out.println("code : " + code);
//		kakaoService.getAccessToken(code);
//		kakaoService.getUserInfo();
//		
//		return "redirect:index";
//	}
//	@Autowired private KakaoService kakaoService;
	
	@RequestMapping("mobileCheck")
	@ResponseBody	
	public String sendSMS(String tel) { // 휴대폰 문자보내기
		Random rand  = new Random(); //랜덤숫자 생성하기 !!
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }
             
    	service.certifiedPhoneNumber(tel, numStr); //휴대폰 api 쪽으로 가기 !!
    	// // 밑에 자세한 설명나옴
     
        return numStr;
    }	
	
	@RequestMapping("userInfo")
	public String userInfo(Model model,  RedirectAttributes ra) {
		model.addAttribute("menu","userInfo");
		String msg = service.userInfo(model);
		if(msg.equals("회원 검색 완료"))
			return "member/userInfo";
		
		ra.addFlashAttribute("msg", msg);
		return "redirect:login";
	}
	
	@RequestMapping("update")
	public String update(Model model) {
		model.addAttribute("menu","update");
		String sessionId = (String)session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		
		return "member/update";
	}
	
	@PostMapping("updateProc")
	public String updateProc(MemberDTO member, String postcode, String detailAddress, Model model) {
		String sessionId = (String)session.getAttribute("id");
		if(member.getAddress() != null && member.getAddress().trim().isEmpty() == false)
			member.setAddress( postcode + "," + member.getAddress() + "," + detailAddress);
		if(sessionId == null)
			return "redirect:login";
		
		member.setId(sessionId);
		String msg = service.updateProc(member);
		if(msg.equals("회원 수정 완료")) {
			//session.invalidate();
			return "redirect:index";
		}
		
		model.addAttribute("msg", msg);
		return "member/update";
	}
	
	@RequestMapping("delete")
	public String delete(Model model) {
		model.addAttribute("menu","delete");
		String sessionId = (String)session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		
		return "member/delete";
	}
	
	@PostMapping("deleteProc")
	public String deleteProc(MemberDTO member, Model model) {
		String sessionId = (String)session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		
		member.setId(sessionId);
		String msg = service.deleteProc(member);
		if(msg.equals("회원 삭제 완료")) {
			session.invalidate();
			return "redirect:index";
		}
		
		model.addAttribute("msg", msg);
		return "member/delete";
	}
	
	@RequestMapping("userHeader")
    public String userHeader() {
    	return "member/userHeader";
    }
	
	@RequestMapping("userHeader2")
    public String userHeader2() {
    	return "member/userHeader2";
    }
    
    @RequestMapping("userFooter")
   	public String userFooter() {
   		return "member/userFooter";
  	}
    
    @RequestMapping("terms")
	public String terms(Model model) {
    	model.addAttribute("menu","regist");
		return "member/terms";
	}
    
    @RequestMapping("myReservation")
    public String myReservation(Model model) {
        model.addAttribute("menu", "myReservation");
        String sessionId = (String) session.getAttribute("id");
        if (sessionId == null)
            return "redirect:login";

        ResponseEntity<List<ReservationDTO>> responseEntity = service.myReservation(sessionId,model);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            List<ReservationDTO> reservationDTOList = responseEntity.getBody();
            model.addAttribute("reservations", reservationDTOList);
            return "member/myReservation";
        } else {
            System.out.println("Request failed. Status code: " + responseEntity.getStatusCode());
            // 에러 페이지로 리다이렉트 또는 적절한 에러 처리 로직 추가
            return "error";
        }
    }
    
    @RequestMapping("cancel")
    public String cancel(ReservationDTO dto) {
    	String sessionId = (String)session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		service.cancel(dto);
    	return "redirect:myReservation";
    }
    
    @RequestMapping("preReservation")
    public String preReservation(Model model) {
    	model.addAttribute("menu", "preReservation");
        String sessionId = (String) session.getAttribute("id");
        if (sessionId == null)
            return "redirect:login";

        ResponseEntity<List<ReservationDTO>> responseEntity = service.preReservation(sessionId);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            List<ReservationDTO> reservationDTOList = responseEntity.getBody();
            model.addAttribute("reservations", reservationDTOList);
            return "member/preReservation";
        } else {
            System.out.println("Request failed. Status code: " + responseEntity.getStatusCode());
            // 에러 페이지로 리다이렉트 또는 적절한 에러 처리 로직 추가
            return "error";
        }
    }
    
    @RequestMapping("myBook")
    public String myBook(Model model) {
    	model.addAttribute("menu","myBook");
    	String sessionId = (String)session.getAttribute("id");
    	if(sessionId == null) 
    		return "redirect:login";
    	
    	service.myBook(model, sessionId);
    	
    	return "member/myBook";
    }
    
    @RequestMapping("borrowDateExtend")
    public String borrowDateExtend(Model model, String no) {
        String sessionId = (String) session.getAttribute("id");
        if (sessionId == null)
            return "redirect:login";
        
        service.borrowDateExtend (no, sessionId);
        //model.addAttribute("board", board);
        return "redirect:myBook";
    }

    @RequestMapping("returnProc2")
	public String returnProc2(String no) {
			
		String sessionId = (String) session.getAttribute("id");
		if (sessionId == null || sessionId.trim().isEmpty()) 
		return "redirect:myBook";
		
		service.returnProc2(no);
		return "redirect:myBook";	
	}
    
    @RequestMapping("findIdPw")
    public String findIdPw(Model model) {
    	model.addAttribute("menu","findIdPw");
    	return "member/findIdPw";
    }
    
    @RequestMapping("findId")
    public String findId(Model model) {
    	model.addAttribute("menu","findIdPw");
    	return "member/findId";
    }
    
    @RequestMapping("findPw")
    public String findPw(Model model) {
    	model.addAttribute("menu","findIdPw");
    	return "member/findPw";
    }
    
    @RequestMapping("mobileCheck2")
	@ResponseBody	
	public String sendSMS2(MemberDTO member, String tel) { // 휴대폰 문자보내기
    	// 이름과 전화번호의 유효성을 검사
        String checkResult = service.nameTelCheck(member);

        if ("success".equals(checkResult)) {
            // 유효하면 휴대폰 번호 전송
            Random rand = new Random();
            String numStr = "";
            for (int i = 0; i < 4; i++) {
                String ran = Integer.toString(rand.nextInt(10));
                numStr += ran;
            }

            service.certifiedPhoneNumber(tel, numStr);
            return numStr;
        } else {
            // 유효하지 않으면 에러 메시지 반환
            return "error";
        }
    }
    
    @RequestMapping("findIdResult")
    public String findIdResult(MemberDTO member, Model model) {
    	model.addAttribute("menu","findIdPw");
    	service.findIdResult(model, member);
    	return "member/findIdResult";
    }
    
    @RequestMapping("mobileCheck3")
	@ResponseBody	
	public String sendSMS3(MemberDTO member, String tel) { // 휴대폰 문자보내기
    	// 이름과 전화번호의 유효성을 검사
        String checkResult = service.idTelCheck(member);

        if ("success".equals(checkResult)) {
            // 유효하면 휴대폰 번호 전송
            Random rand = new Random();
            String numStr = "";
            for (int i = 0; i < 4; i++) {
                String ran = Integer.toString(rand.nextInt(10));
                numStr += ran;
            }

            service.certifiedPhoneNumber(tel, numStr);
            return numStr;
        } else {
            // 유효하지 않으면 에러 메시지 반환
            return "error";
        }
    }
    
    @RequestMapping("findPwResult")
    public String findPwResult(MemberDTO member, Model model) {
    	model.addAttribute("menu","findIdPw");
    	Random rand  = new Random(); //랜덤숫자 생성하기 !!
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }
    	service.findPwResult(model, member, numStr);
    	return "member/findPwResult";
    }
}
