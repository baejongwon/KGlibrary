package com.kg.library.notice;

/*
create table noticeboard_cal(
startDate varchar2(10),
endDate varchar2(10),
events varchar2(100));

마리아db
CREATE TABLE IF NOT EXISTS noticeboard_cal (
    startDate DATE,
    endDate DATE,
    events VARCHAR(100)
);

 */

public class productionDTO {
	private String startDate;//제목
	private String endDate;//작성자
	private String events;//내용
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEvents() {
		return events;
	}
	public void setEvents(String events) {
		this.events = events;
	}
	
	
	
}
