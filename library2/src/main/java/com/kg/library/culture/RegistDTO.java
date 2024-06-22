package com.kg.library.culture;

/*
no 넘버
id 아이디
title 제목
name 이름
p_name 보호자 이름
tel 전화번호
email 이메일
address 주소
es_school 학교
es_hak 학년
es_ban 반
contents 내용 
apply_ck 중복체크
 * */

/*
  CREATE TABLE regist (
    no number primary key,
    id VARCHAR2(20),
    title VARCHAR2(100),    
    name VARCHAR2(20),
    p_name VARCHAR2(20),
    tel VARCHAR2(50),
    email VARCHAR2(50),
    address VARCHAR2(100),
    es_school VARCHAR2(100),
    es_hak VARCHAR2(100),
    es_ban VARCHAR2(100),
    contents VARCHAR2(4000),
    apply_ck varchar2(2)
);

마리아db

CREATE TABLE IF NOT EXISTS regist (
    no INT AUTO_INCREMENT PRIMARY KEY,
    id VARCHAR(20),
    title VARCHAR(100),
    name VARCHAR(20),
    p_name VARCHAR(20),
    tel VARCHAR(50),
    email VARCHAR(50),
    address VARCHAR(100),
    es_school VARCHAR(100),
    es_hak VARCHAR(100),
    es_ban VARCHAR(100),
    contents TEXT,
    apply_ck VARCHAR(2)
);

 */
public class RegistDTO {
	private int no;
	private String id;
	private String title;
	private String name;
	private String p_name;
	private String tel;
	private String email;
	private String address;
	private String es_school;
	private String es_hak;
	private String es_ban;
	private String contents;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEs_school() {
		return es_school;
	}
	public void setEs_school(String es_school) {
		this.es_school = es_school;
	}
	public String getEs_hak() {
		return es_hak;
	}
	public void setEs_hak(String es_hak) {
		this.es_hak = es_hak;
	}
	public String getEs_ban() {
		return es_ban;
	}
	public void setEs_ban(String es_ban) {
		this.es_ban = es_ban;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	
}
