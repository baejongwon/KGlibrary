package com.kg.library.member;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.ui.Model;

//import com.kg.library.book.BookDTO;

@Mapper
public interface IMemberMapper {

	int joinProc(MemberDTO member);

	MemberDTO login(String id);

	ArrayList<MemberDTO> memberInfo(@Param("begin")int begin, @Param("end")int end,
			@Param("select")String select, @Param("search")String search);

	int totalCount(@Param("select")String select, @Param("search")String search);

	int updateProc(MemberDTO member);

	int deleteProc(String id);
	
	MemberDTO findId(String name, String tel);
	
	String findIdResult(@Param("name")String name, @Param("tel")String tel);

	MemberDTO findPw(String id, String tel);

	int findPwResult(@Param("id")String id, @Param("tel")String tel, @Param("secretPass")String secretPass);

}