package com.kg.library.notice;

/*
	create table ask_board(
no number primary key,
title varchar2(100) not null,
id varchar2(20) not null,
content varchar2(3000) not null,
writeDate varchar2(15) not null,
hits varchar2(255),
image varchar2(255),
ref number(5),
step number(3),
depth number(3)
);
CREATE SEQUENCE ask_board_seq START  WITH 1 INCREMENT BY 1 NOMAXVALUE NOCACHE NOCYCLE;
commit;

마리아db

CREATE TABLE IF NOT EXISTS ask_board (
    no INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    id VARCHAR(20) NOT NULL,
    content VARCHAR(3000) NOT NULL,
    writeDate VARCHAR(15) NOT NULL,
    hits VARCHAR(255),
    image VARCHAR(255),
    ref INT,
    step INT,
    depth INT
);


 */
public class Ask_board_DTO {
	private int no;
	private String title;//제목
	private String id;//작성자
	private String content;//내용
	private String writeDate;//작성시간
	private String hits;//조회수
	private String image;//이미지
	private int ref;//답변
	private int step;//같은 계층(depth)의 답변에 대한 순서를 표현하기 위해..
	private int depth;//답변에 답변 그것에 답변...을 표현하기 위해
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	public String getHits() {
		return hits;
	}
	public void setHits(String hits) {
		this.hits = hits;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	
}