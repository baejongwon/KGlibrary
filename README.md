# 배종원의 프로젝트 입니다.



<h3>사용 기술 및 개발환경</h3>
server : Apach Tomcat 9 <br>
DB : MariaDB 10.6.14 <br>
Data Store : Redis<br>
Framework/Flatform : Spring MVC, SpringSecurity, MyBatis, Bootstrap, jQuery, Jsp, RESTful API<br>
Language : JAVA(version 17), Javascript, HTML5, CSS3<br>
Tool :  Git, GitHub,SQL Developer<br>

<h3>내용</h3>

<ul>
  <li><h4>구현 기능</h4></li>
  1.회원 가입 및 관리(member)<br>
  ㄴ 로그인 / 회원가입 / 아이디,비밀번호 찾기 / 회원 수정 / 내 예약 조회 / 지난 예약 조회 / 대출 목록 / 회원 탈퇴 / 회원 관리(관리자만)<br>
  <br>
  2. 이용안내(guide)<br>
  ㄴ 열람실 이용 안내 / 회원가입 안내 / 도서 대출 반납 안내<br>
  <br>
  3. 도서관 소개(introduction)<br>
  ㄴ 인사말 / 자료 현황 / 찾아오시는길<br>
  <br>
  4. 이용자마당(notice)<br>
  ㄴ 공지사항 / 묻고 답하기 / 도서관 일정<br>
  <br>
  5. 문화 마당(culture)<br>
  ㄴ 문화 행사
  
<h5>application.properties에는 aws의 rds 접속 정보와 access-key, secret-key가 포함되어 github에는 포함되어 있지 않습니다.</h5>

```properties
# OracleDB connection settings
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

# JSP setting
spring.mvc.view.prefix=/jsp/
spring.mvc.view.suffix=.jsp

# mybatis framework setting
mybatis.mapper-locations=/mappers/*.xml
 
# file settings
spring.servlet.multipart.max-file-size=10MB

# server port settings
server.port=80

coolsms.apikey=
coolsms.apisecret=
coolsms.fromnumber=

spring.redis.cluster.nodes=redis-cluster.delivery.svc.cluster.local:6379

# AWS S3 Configuration
aws.access-key=
aws.secret-key=
aws.region=
aws.s3.bucket-name=
