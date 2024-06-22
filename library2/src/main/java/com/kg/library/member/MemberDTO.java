package com.kg.library.member;
/*
* 회원가입(member)
*회원가입 순서 - 번호(1),이름(2),아이디(3),비밀번호(4),연락처(5),Email(6),주소(7),대여도서(8),스터디룸(9)

(예시

-oracle_ver-
create table member(
id varchar2(20) not null primary key,
pw varchar2(100) not null,
name varchar2(30) not null,
email varchar2(50) not null,
address varchar2(100),
tel varchar2(20) not null
);


-mariadb ver-
CREATE TABLE member (
    id VARCHAR(20) NOT NULL PRIMARY KEY,
    pw VARCHAR(100) NOT NULL,
    name VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL,
    address VARCHAR(100),
    tel VARCHAR(20) NOT NULL
);

*/

public class MemberDTO {
	private String id;
	private String pw;
	private String confirm;
	private String name;
	private String email;
	private String address;
	private String tel;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
}
