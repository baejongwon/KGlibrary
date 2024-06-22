package com.kg.library.culture;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kg.library.member.MemberDTO;

@Mapper
public interface CultureMapper {

	ArrayList<CultureDTO> cultureboard(int begin, int end, String search_select, String search);

	int totalCount(String search_select, String search);

	void culture_writeProc(CultureDTO board);

	CultureDTO cultureContent(int n);

	void CultureDeleteProc(int n);

	void cultureModifyPro(CultureDTO board);

	MemberDTO login(String sessionId);

	void registWritePro(RegistDTO r_board);

	String applicants_count(CultureDTO c_board);

	String registCount(@Param(value = "title") String title, @Param(value = "sessionId") String sessionId);

	RegistDTO registContent(int n);

	String apply_ck(String title, String sessionId);

	String applicants(String title);

	void updateApplicantsCount(@Param(value = "applicants") String applicants, @Param(value = "no") int no);

	RegistDTO registboard(@Param(value = "title") String title, @Param(value = "sessionId") String sessionId);

	void registDelete(@Param(value = "title") String title, @Param(value = "sessionId") String sessionId);

	ArrayList<CultureDTO> main_board();



}
