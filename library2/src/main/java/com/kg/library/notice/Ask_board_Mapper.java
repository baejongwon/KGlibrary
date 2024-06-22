package com.kg.library.notice;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface Ask_board_Mapper {

	ArrayList<Ask_board_DTO> ask_board(int begin, int end, String search_select, String search);

	int totalCount(String search_select, String search);

	void ask_board_writeProc(Ask_board_DTO board);

	void incrementHits(int n);

	Ask_board_DTO ask_board_Content(int n);

	void ContentDeleteProc(int n);

	int ask_board_ModifyProc(Ask_board_DTO board);

	void updateStep(@Param("ref")int ref, @Param("step")int step);
	
	int maxNum();

}
