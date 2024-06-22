package com.kg.library.culture;

/*
넘버 no 
제목 title
작성자 id
강좌 기간 시작일 class_period_start
강좌 기간 종료일 class_period_end
신청 기간 시작일 reservation_period_start
신청 기간 종료일 reservation_period_end
수강 대상 target
행사 장소 event_area
접수 장소 reception_area
신청 인원 applicants
모집 인원 people
참가비 money
강사 teacher
내용 contents
이미지 image
 * */


/*
  CREATE TABLE culture (
    no number primary key,
    title VARCHAR2(100),
    id VARCHAR2(20),
    class_period_start VARCHAR2(50),
    class_period_end VARCHAR2(50),
    reservation_period_start VARCHAR2(50),
    reservation_period_end VARCHAR2(50),
    target VARCHAR2(100),
    event_area VARCHAR2(100),
    reception_area VARCHAR2(100),
    people VARCHAR2(50),
    money VARCHAR2(50),
    teacher VARCHAR2(100),
    contents VARCHAR2(4000),
    image VARCHAR2(200),
    applicants VARCHAR2(50),
    select_cate varchar2(10),
    writeDate varchar2(50)
);

마리아db 
	CREATE TABLE culture (
    no INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    id VARCHAR(20),
    class_period_start VARCHAR(50),
    class_period_end VARCHAR(50),
    reservation_period_start VARCHAR(50),
    reservation_period_end VARCHAR(50),
    target VARCHAR(100),
    event_area VARCHAR(100),
    reception_area VARCHAR(100),
    people VARCHAR(50),
    money VARCHAR(50),
    teacher VARCHAR(100),
    contents TEXT,
    image VARCHAR(200),
    applicants VARCHAR(50),
    select_cate VARCHAR(10),
    writeDate VARCHAR(50)
);

 */
public class CultureDTO {
	private int no;
	private String title;
	private String id;
	private String class_period_start;
	private String class_period_end;
	private String class_time;
	private String reservation_period_start;
	private String reservation_period_end;
	private String target;
	private String event_area;
	private String reception_area;
	private String applicants;
	private String people;
	private String money;
	private String category;
	private String teacher;
	private String contents;
	private String image;
	private String select_cate;
	private String writeDate;
	
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
	public String getClass_period_start() {
		return class_period_start;
	}
	public void setClass_period_start(String class_period_start) {
		this.class_period_start = class_period_start;
	}
	public String getClass_period_end() {
		return class_period_end;
	}
	public void setClass_period_end(String class_period_end) {
		this.class_period_end = class_period_end;
	}
	public String getClass_time() {
		return class_time;
	}
	public void setClass_time(String class_time) {
		this.class_time = class_time;
	}
	public String getReservation_period_start() {
		return reservation_period_start;
	}
	public void setReservation_period_start(String reservation_period_start) {
		this.reservation_period_start = reservation_period_start;
	}
	public String getReservation_period_end() {
		return reservation_period_end;
	}
	public void setReservation_period_end(String reservation_period_end) {
		this.reservation_period_end = reservation_period_end;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getEvent_area() {
		return event_area;
	}
	public void setEvent_area(String event_area) {
		this.event_area = event_area;
	}
	public String getReception_area() {
		return reception_area;
	}
	public void setReception_area(String reception_area) {
		this.reception_area = reception_area;
	}
	
	public String getApplicants() {
		return applicants;
	}
	public void setApplicants(String applicants) {
		this.applicants = applicants;
	}
	public String getPeople() {
		return people;
	}
	public void setPeople(String people) {
		this.people = people;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getSelect_cate() {
		return select_cate;
	}
	public void setSelect_cate(String select_cate) {
		this.select_cate = select_cate;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

}
