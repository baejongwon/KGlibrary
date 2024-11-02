# 배종원의 프로젝트 KGlibrary 입니다.

📓 포트폴리오: https://drive.google.com/file/d/1b4cy2eNZ_HXo6_P5PQZ4FtA8tG-46PN5/view?usp=drive_link

### 목차

> 1. [사용 기술 및 개발환경](#사용-기술-및-개발환경)
> 2. [인프라 아키텍처](#인프라-아키텍처)
> 3. [구현 기능](#구현-기능)
>   + [회원 가입 및 관리](#회원-가입-및-관리member)
>   + [이용안내](#이용안내guide)
>   + [도서관 소개](#도서관-소개introduction)
>   + [이용자 마당](#이용자마당notice)
>   + [문화 마당](#문화-마당culture)
> 4. [프로젝트를 하며 느낀 점](#프로젝트를-하며-느낀-점)



# 사용 기술 및 개발환경
server : Apach Tomcat 9 <br>
DB : MariaDB 10.6.14 <br>
Data Store : Redis(login session관리)<br>
Framework/Flatform : Spring MVC, SpringSecurity, MyBatis, Bootstrap, jQuery, Jsp, RESTful API<br>
Language : JAVA(version 17), Javascript, HTML5, CSS3<br>
Tool :  Git, GitHub,SQL Developer<br>
CI/CD : Jenkins, ArgoCD
AWS : EC2 / RDS / Load Balancer / Auto Scaling Group / S3 / ECR / EKS

# 인프라-아키텍처
<img src="https://github.com/baejongwon/jongwon-git-img/blob/main/Infrastructure%20Architecture.jpg" width=1200px alt="아키텍처"> 

# CI/CD 구성
<img src="https://github.com/baejongwon/jongwon-git-img/blob/main/cicd.png" width=1200px alt="cicd구성"> 

### ECR로 도커 이미지 관리
- 젠킨스에서 ECR로 도커이미지를 push --> AgroCD에서 ECR에 있는 이미지를 사용해서 배포하도록 하였습니다.
<img src="https://github.com/baejongwon/jongwon-git-img/blob/main/ECR.jpg" width=1200px alt="ECR이미지관리"> 


# 구현 기능
  
  ### 회원 가입 및 관리(member)
  [공통]
  - 사용자는 약관에 모두 동의 후 아이디 / 비밀번호 / 비밀번호 확인 / 이름 / 이메일 / 우편번호 / 주소 / 상세주소 / 전화번호 인증 후 가입
  - 비밀번호는 암호화되어 DB에 저장
  - 회원가입 시 사용한 아이디와 비밀번호로 로그인
  - 아이디나 비밀번호 찾기 시 아이디는 이름과 전화번호 / 비밀번호는 아이디와 전화번호를 사용해 찾기 가능
  - 마이페이지에서 내 정보 보기, 회원 수정, 내 예약 조회, 지난 예약, 도서 대출 목록, 회원 탈퇴가 가능
  - 카카오 아이디로 로그인이 가능

  [관리자]
  - 관리자 페이지에서 회원 관리 가능

  ### 이용안내(guide)

  - 열람실 이용안내 / 회원가입 안내 / 도서대출/반납 안내를 확인 가능

  ### 도서관 소개(introduction)
  - 인사말 확인 가능
  - 자료 현황 : 등록된 도서들을 카테고리별로 갯수 확인 가능
  - 찾오시는길 : 도서관의 위치를 지도로 확인 가능
  
  ### 이용자마당(notice)

  [사용자]
  
  - 공지사항 게시판에서 제목, 내용, 업로드할 파일을 선택 후 글 작성 가능
  - 자신이 올린 게시글만 수정 및 삭제 가능
  - 묻고 답하기 게시판에서 제목, 내용, 업로드할 파일을 선택 후 글 작성 가능
  - 타인의 게시글에도 댓글 작성 가능
  - 도서관 일정에서 달력으로 도서관의 일정 확인 가능
    
  [관리자]

  - 공지사항 게시판, 묻고 답하기 게시판에서 회원들의 게시글 삭제 가능
  - 도서관 일정 추가 가능
  
  ### 문화 마당(culture)
  
 [사용자]

 - 등록된 행사에 행사 신청 가능
 - 신청 기간이 지난 게시글은 행사 신청 불가능
 - 모집 인원이 모두 찬 게시글은 행사 신청 불가능
 - 자신이 신청한 행사 확인 및 삭제 가능

 [관리자]

 - 제목, 행사기간, 신청기간, 수강대상, 행사장소, 접수장소, 모집 인원, 참가비, 강사명, 내용, 업로드 파일 선택 후 행사 게시글 작성 가능

# 프로젝트를 하며 느낀 점
첫 팀 프로젝트를 진행하며 처음에는 어색함과 새로운 기술에 대한 낯설음 때문에 효율적으로 진행되지 않았습니다.<br>
하지만 회의를 통해 팀원들과 소통하고 역할을 명확히 분담하면서 프로젝트가 점점 개선되는 모습을 보며 성취감을 느낄 수 있었습니다.<br>
특히, 각자의 역할이 명확해지면서 서로의 강점을 활용하고 약점을 보완해나가는 과정을 통해 협업의 중요성을 깊이 깨닫게 되었고,<br>
원활한 소통이 프로젝트의 완성도와 효율성에 얼마나 큰 영향을 미치는지 실감할 수 있었습니다.<br>
이 경험은 분명 앞으로 다양한 팀과 협력하며 프로젝트를 진행하는데 좋은 밑거름이 될거라고 생각합니다.


