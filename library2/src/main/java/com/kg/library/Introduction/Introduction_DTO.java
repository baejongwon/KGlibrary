package com.kg.library.Introduction;

/*
create table notice(
no number primary key,
title varchar2(100) not null,
id varchar2(20) not null,
content varchar(3000) not null,
writeDate varchar2(15) not null,
hits varchar2(255),
image varchar2(255));

마리아db

CREATE TABLE IF NOT EXISTS notice (
    no INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    id VARCHAR(20) NOT NULL,
    content TEXT NOT NULL,
    writeDate VARCHAR(15) NOT NULL,
    hits VARCHAR(255),
    image VARCHAR(255)
);

 */

public class Introduction_DTO {
	private int no;
	private String title;//제목
	private String id;//작성자
	private String content;//내용
	private String writeDate;//작성시간
	private String hits;//조회수
	private String image;//이미지
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
	
	
}
