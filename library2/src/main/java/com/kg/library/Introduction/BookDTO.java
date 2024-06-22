package com.kg.library.Introduction;
import java.sql.Timestamp;

/*
*도서 등록(book)
순서 - 번호(1),카테고리(2),제목(3),저작자(4),발행자(5),발행년도(6),비치일(7),상세페이지경로(8),이미지(9), 빌린 사람(10) ,책 번호(책 갯수)(11), 기증자(12),빌린 시간(13),추천 도서(14)

create table book(
no number not null,
category varchar2(50),
title_info varchar2(500),
author_info varchar2(500),
pub_info varchar2(500),
pub_year_info varchar2(50),
reg_date varchar2(50),
detail_link varchar2(200),
image varchar2(255),
borrowperson varchar2(50),
book_count number,
donation varchar2(50),
borrowdate timestamp,
rentaldate VARCHAR2(50),
hitbook varchar2(50)
);
alter table book modify title_info varchar2(500);
alter table book modify author_info varchar2(500);
alter table book modify pub_info varchar2(500);
ALTER TABLE book ADD rentaldate VARCHAR2(50);
alter table book add hitbook varchar2(50);
alter table book modify detail_link varchar2(1000);

*/
public class BookDTO {

	private int no;
	private String category;
	private String title_info;
	private String author_info;
	private String pub_info;
	private String pub_year_info;
	private String reg_date;
	private String detail_link;
	private String image;
	private String borrowperson;
	private int book_count;
	private String donation;
	private Timestamp borrowdate;
	private String rentaldate;
	private String hitbook;
	
	
	public String getHitbook() {
		return hitbook;
	}
	public void setHitbook(String hitbook) {
		this.hitbook = hitbook;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle_info() {
		return title_info;
	}
	public void setTitle_info(String title_info) {
		this.title_info = title_info;
	}
	public String getAuthor_info() {
		return author_info;
	}
	public void setAuthor_info(String author_info) {
		this.author_info = author_info;
	}
	public String getPub_info() {
		return pub_info;
	}
	public void setPub_info(String pub_info) {
		this.pub_info = pub_info;
	}
	public String getPub_year_info() {
		return pub_year_info;
	}
	public void setPub_year_info(String pub_year_info) {
		this.pub_year_info = pub_year_info;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getDetail_link() {
		return detail_link;
	}
	public void setDetail_link(String detail_link) {
		this.detail_link = detail_link;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getBorrowperson() {
		return borrowperson;
	}
	public void setBorrowperson(String borrowperson) {
		this.borrowperson = borrowperson;
	}
	public int getBook_count() {
		return book_count;
	}
	public void setBook_count(int book_count) {
		this.book_count = book_count;
	}
	public String getDonation() {
		return donation;
	}
	public void setDonation(String donation) {
		this.donation = donation;
	}
	public Timestamp getBorrowdate() {
		return borrowdate;
	}
	public void setBorrowdate(Timestamp borrowdate) {
		this.borrowdate = borrowdate;
	}
	public String getRentaldate() {
		return rentaldate;
	}
	public void setRentaldate(String rentaldate) {
		this.rentaldate = rentaldate;
	}
}
