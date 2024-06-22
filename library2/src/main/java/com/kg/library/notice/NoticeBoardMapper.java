package com.kg.library.notice;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeBoardMapper {

	ArrayList<NoticeBoardDTO> noticeBoard(int begin, int end, String search_select, String search);

	int totalCount(String search_select, String search);

	void noticeboard_writeProc(NoticeBoardDTO board);

	void incrementHits(int n);

	NoticeBoardDTO noticeboard_Content(int n);

	void ContentDeleteProc3(int n);

	int noticeboard_ModifyProc(NoticeBoardDTO board);

	ArrayList<NoticeBoardDTO> main_board();

}
