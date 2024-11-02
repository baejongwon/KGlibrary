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
>
> 4. [프로젝트 느낀점](프로젝트를-하며-느낀-점)



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


<br>


<!-- 
<ul>
<li><h4>메인 페이지에 추천도서 / 신작도서 정보 요청</h4></li>

요청<br>
Controller

```
	@RequestMapping("main")
	public String main(Model model) {
		notice_service.main_board(model);
		clture_Service.main_board(model);
		main_Service.hit_book(model);
		main_Service.new_book(model);
		return "default/main";
	}
```


Service

```
	 	public void hit_book(Model model) {

		try {

			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			ResponseEntity<ArrayList<BookDTO>> responseEntity = restTemplate.exchange(
					"https://www.jongwon-project.link/book/hit_book", HttpMethod.GET, new HttpEntity<>(headers),
					new ParameterizedTypeReference<ArrayList<BookDTO>>() {
					});

			ArrayList<BookDTO> hitbooks = responseEntity.getBody();
			ArrayList<String> imageUrls = new ArrayList<>();

			if (hitbooks != null) {
				for (BookDTO b : hitbooks) {
					if (b.getImage() == null || b.getImage().trim().isEmpty()) {
						b.setImage("20240109150111-40641325628.20230718121618.jpg");
						continue;
					}
					System.out.println("받아온 hitbook : "+ b.getImage());
					b.setImage(getS3ObjectUri(b.getImage()));
				}
			}
			// 모델에 데이터 추가
			model.addAttribute("hitbooks", hitbooks);
			model.addAttribute("imageUrls", imageUrls);

		} catch (HttpClientErrorException.NotFound notFoundException) {
			// 404 Not Found 에러 처리
			logger.error("Server returned 404 Not Found");
		} catch (Exception e) {
			// 기타 예외 처리
			logger.error("An error occurred while fetching data from the server", e);
		}

	}

	public void new_book(Model model) {

		try {

			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			ResponseEntity<ArrayList<BookDTO>> responseEntity = restTemplate.exchange(
					"https://www.jongwon-project.link/book/new_Book", HttpMethod.GET, new HttpEntity<>(headers),
					new ParameterizedTypeReference<ArrayList<BookDTO>>() {
					});

			ArrayList<BookDTO> newBooks = responseEntity.getBody();
			ArrayList<String> imageUrls = new ArrayList<>();
			
			if (newBooks != null) {
				for (BookDTO b : newBooks) {
					if (b.getImage() == null || b.getImage().trim().isEmpty()) {
						b.setImage("20240109150111-40641325628.20230718121618.jpg");
						continue;
					}
					if (b.getCategory().equals("API")) // API에서 받아온 이미지 라면
					{
						continue;
					}
					String imageUrl = getS3ObjectUri(b.getImage());
					imageUrls.add(imageUrl);
				}
			}
			model.addAttribute("newBooks", newBooks);
			model.addAttribute("imageUrls", imageUrls);

		} catch (HttpClientErrorException.NotFound notFoundException) {
			logger.error("Server returned 404 Not Found");
		} catch (Exception e) {
			logger.error("An error occurred while fetching data from the server", e);
		}
	}
```

응답<br>
Controller

```
	@GetMapping("book/hit_book")
	 @ResponseBody
	 public ResponseEntity<ArrayList<BookDTO>> hitBook()  {
		   try {
			   System.err.println("hit book 요청 연결 성공");
	            ArrayList<BookDTO> hitbooks = service.hitBook();

	            // 받아온 데이터 출력
	            for (BookDTO b : hitbooks) {
	                System.out.println("No: " + b.getNo());
	                System.out.println("Image: " + b.getImage());
	                System.out.println("Title Info: " + b.getTitle_info());
	                System.out.println("Author Info: " + b.getAuthor_info());
	            }

	            return ResponseEntity.ok(hitbooks); // 정상적인 응답
	        } catch (Exception e) {
	            // 예외 발생 시 출력
	            System.err.println("hit book 요청 연결 실패");
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
	        }
	    }
	 @GetMapping("book/new_Book")
	 @ResponseBody
	    public ResponseEntity<ArrayList<BookDTO>> newBook() {
		   try {
			   System.err.println("new book 요청 연결 성공");
	            ArrayList<BookDTO> newBooks = service.newBook();

	            for (BookDTO b : newBooks) {
	            	System.out.print("\u001B[31m");
	                System.out.println("8087newbook No: " + b.getNo());
	                System.out.println("8087newbook Image: " + b.getImage());
	                System.out.println("8087newbook Title Info: " + b.getTitle_info());
	                System.out.println("8087newbook Author Info: " + b.getAuthor_info());
	                System.out.print("\u001B[0m");
	            }

	            return ResponseEntity.ok(newBooks);
	        } catch (Exception e) {
	            System.err.println("new book 요청 연결 실패");
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
	        }
	    }
	
```

Service

```
public ArrayList<BookDTO> hitBook() {

		ArrayList<BookDTO> hitbooks = mapper.hitbooks();
		if (hitbooks != null) {
			for (BookDTO b : hitbooks) {
				if (b.getImage() == null || b.getImage().trim().isEmpty()) {
					b.setImage("20240109150111-40641325628.20230718121618.jpg");
					continue;
				}

			}
		}
		return hitbooks;
	}
	public ArrayList<BookDTO> newBook() {

		ArrayList<BookDTO> newBooks = mapper.newbooks();
		if (newBooks != null) {
			for (BookDTO b : newBooks) {
				if (b.getImage() == null || b.getImage().trim().isEmpty()) {
					b.setImage("20240109150111-40641325628.20230718121618.jpg");
					continue;
				}

			}
		}
		return newBooks;
	}
```

mapper

```
	<select id="hitbooks" resultType="com.kg.library_1.book.BookDTO">
		SELECT * FROM book WHERE hitbook = 'yes' AND book_count = 1 ORDER BY no DESC LIMIT 3
	</select>

	<select id="newbooks" resultType="com.kg.library_1.book.BookDTO">
		SELECT * FROM book WHERE book_count = 1 ORDER BY reg_date DESC LIMIT 3
	</select>
```


<ul>
<li><h4>이용자 마당 </h4></li>

-->



<ul>
<!-- 
# 테라폼 코드

## vpc
  
```
terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

# Configure the AWS Provider
provider "aws" {
  region  = "ap-northeast-2"
  profile = "admin"
}

module "vpc"{ 
source = "terraform-aws-modules/vpc/aws" 
version = "~> 5.0" 

name = "eks-vpc"
cidr = "172.28.0.0/16"
azs = ["ap-northeast-2a", "ap-northeast-2c"]
private_subnets = ["172.28.1.0/24","172.28.3.0/24" ]
public_subnets = ["172.28.101.0/24","172.28.103.0/24"]

}
module "BastionHost_SG" {
  source          = "terraform-aws-modules/security-group/aws"
  version         = "~> 5.0"
  name            = "BastionHost_SG"
  description     = "BastionHost_SG"
  vpc_id          = module.vpc.vpc_id
  use_name_prefix = false

  ingress_with_cidr_blocks = [
    {
      from_port   = 8080
      to_port     = 8080
      protocol    = "tcp"
      cidr_blocks = "0.0.0.0/0"
    },
    {
      from_port   = 22
      to_port     = 22
      protocol    = "tcp"
      cidr_blocks = "0.0.0.0/0"
    },
    {
      from_port   = -1
      to_port     = -1
      protocol    = "icmp"
      cidr_blocks = "0.0.0.0/0"
    }
  ]
  egress_with_cidr_blocks = [
    {
      from_port   = 0
      to_port     = 0
      protocol    = "-1"
      cidr_blocks = "0.0.0.0/0"
    }
  ]
}
data "aws_key_pair" "ec2-key" {
  key_name = "bootServer"
}

resource "aws_launch_template" "bastion-lt" {
  name = "bastion-lt"
  image_id = "ami-032a9a0e2fb604b62"
  instance_type = "t3.medium"
  key_name = "bootServer"
  tags = {
    Name = "bastion-lt"
  }
  network_interfaces {
    associate_public_ip_address = true
    security_groups = [module.BastionHost_SG.security_group_id]
  }
}

resource "aws_autoscaling_group" "bastion-asg" {
  name = "bastion-asg"
  launch_template {
    id=aws_launch_template.bastion-lt.id
    version=aws_launch_template.bastion-lt.latest_version
  }
  vpc_zone_identifier = module.vpc.public_subnets
  min_size = 1
  max_size = 1
}

module "NAT_SG" {
  source          = "terraform-aws-modules/security-group/aws"
  version         = "~> 5.0"
  name            = "NAT_SG"
  vpc_id          = module.vpc.vpc_id
  use_name_prefix = false

  ingress_with_cidr_blocks = [
    {
      from_port   = 0
      to_port     = 0
      protocol    = "-1"
      cidr_blocks = module.vpc.private_subnets_cidr_blocks[0]
    },
    {
      from_port   = 0
      to_port     = 0
      protocol    = "-1"
      cidr_blocks = module.vpc.private_subnets_cidr_blocks[1]
    }
  ]
  egress_with_cidr_blocks = [
    {
      from_port   = 0
      to_port     = 0
      protocol    = "-1"
      cidr_blocks = "0.0.0.0/0"
    }
  ]
}

resource "aws_instance" "nat_ec2" {
  ami                         = "ami-0f4c2e6aee30ccae8"
  subnet_id                   = module.vpc.public_subnets[1]
  instance_type               = "t2.micro"
  key_name                    = data.aws_key_pair.ec2-key.key_name
  source_dest_check           = false
  associate_public_ip_address = true
  vpc_security_group_ids      = [module.NAT_SG.security_group_id]
  tags = {
    Name = "nat-ec2"
  }
}

# Private Subnet Routing Table ( dest: NAT Instance ENI )
data "aws_route_table" "private_0" {
  subnet_id  = module.vpc.private_subnets[0]
  depends_on = [module.vpc]
}

data "aws_route_table" "private_1" {
  subnet_id  = module.vpc.private_subnets[1]
  depends_on = [module.vpc]
}

resource "aws_route" "private_subnet_0" {
  route_table_id         = data.aws_route_table.private_0.id
  destination_cidr_block = "0.0.0.0/0"
  network_interface_id   = aws_instance.nat_ec2.primary_network_interface_id
  depends_on             = [module.vpc, aws_instance.nat_ec2]
}

resource "aws_route" "private_subnet_1" {
  route_table_id         = data.aws_route_table.private_1.id
  destination_cidr_block = "0.0.0.0/0"
  network_interface_id   = aws_instance.nat_ec2.primary_network_interface_id
  depends_on             = [module.vpc, aws_instance.nat_ec2]
}

// Private Subnet Tag ( AWS Load Balancer Controller Tag / internal )
resource "aws_ec2_tag" "private_subnet_tag1" {
  resource_id = module.vpc.private_subnets[0]
  key         = "kubernetes.io/role/internal-elb"
  value       = "1"
}
resource "aws_ec2_tag" "private_subnet_tag2" {
  resource_id = module.vpc.private_subnets[1]
  key         = "kubernetes.io/role/internal-elb"
  value       = "1"
}

// Public Subnet Tag ( AWS Load Balancer Controller Tag / internet-facing )
resource "aws_ec2_tag" "public_subnet_tag1" {
  resource_id = module.vpc.public_subnets[0]
  key         = "kubernetes.io/role/elb"
  value       = "1"
}
resource "aws_ec2_tag" "public_subnet_tag2" {
  resource_id = module.vpc.public_subnets[1]
  key         = "kubernetes.io/role/elb"
  value       = "1"
}



//젠킨스 ec2
module "Jenkins_SG" {
  source          = "terraform-aws-modules/security-group/aws"
  version         = "~> 5.0"
  name            = "Jenkins_SG"
  description     = "Security group for Jenkins EC2 instance"
  vpc_id          = module.vpc.vpc_id
  use_name_prefix = false

  ingress_with_cidr_blocks = [
    {
      from_port   = 8080
      to_port     = 8080
      protocol    = "tcp"
      cidr_blocks = "0.0.0.0/0"
    },
    {
      from_port   = 22
      to_port     = 22
      protocol    = "tcp"
      cidr_blocks = "0.0.0.0/0"
    },
    {
      from_port   = -1
      to_port     = -1
      protocol    = "icmp"
      cidr_blocks = "0.0.0.0/0"
    }
  ]
  egress_with_cidr_blocks = [
    {
      from_port   = 0
      to_port     = 0
      protocol    = "-1"
      cidr_blocks = "0.0.0.0/0"
    }
  ]
}

resource "aws_instance" "jenkins_it" {
  ami                         = "ami-03c61a9f49c571621"
  subnet_id                   = module.vpc.public_subnets[0]
  instance_type               = "t3.large"
  key_name                    = data.aws_key_pair.ec2-key.key_name
  associate_public_ip_address = true
  vpc_security_group_ids      = [module.Jenkins_SG.security_group_id]
  tags = {
    Name = "jenkins-it"
  }
}

resource "aws_db_subnet_group" "main" {
  name       = "main"
  subnet_ids = [
    module.vpc.public_subnets[0], # ap-northeast-2a
    module.vpc.public_subnets[1]  # ap-northeast-2c
  ]

  tags = {
    Name = "main"
  }
}

//eks연결부
module "HA" {
  source               = "../eks"
  vpc_id               = module.vpc.vpc_id
  private_subnets = module.vpc.private_subnets
  vpc_cidr_block = module.vpc.vpc_cidr_block
}
```

## eks

```
module "eks" {
  source  = "terraform-aws-modules/eks/aws" //모듈의 버전
  version = "~> 19.0"

  #EKS Cluster Setting
  cluster_name    = "my-eks"
  cluster_version = "1.28" //1.28 최신
  vpc_id = var.vpc_id // eks workernode 생성할 vpc
  subnet_ids = var.private_subnets
  
  #OIDV(OpenID Connect)구성
  enable_irsa = true

  #EKS Worker Node 정의 (ManagedNode방식 / Launch Template 자동 구성)
  eks_managed_node_groups = {
    initial = {   
      
      min_size     = 2
      max_size     = 3
      desired_size = 2
      instance_types = ["t3.large"]
      vpc_security_group_ids = [module.add_node_sg.security_group_id]
    }
  }

 # public-subnet(bastion)과 API와 통신하기 위해 설정(443)
  cluster_additional_security_group_ids = [module.add_cluster_sg.security_group_id]
  cluster_endpoint_public_access        = true 


  # K8s ConfigMap Object "aws_auth" 구성
  manage_aws_auth_configmap = true
  aws_auth_users = [
    {
      userarn  = "arn:aws:iam::${data.aws_iam_user.EKS_Admin_ID.id}:user/admin"
      username = "admin"
      groups   = ["system:masters"]
    }
  ]
}


provider "kubernetes" {
  host                   = module.eks.cluster_endpoint
  cluster_ca_certificate = base64decode(module.eks.cluster_certificate_authority_data)
  exec {
    api_version = "client.authentication.k8s.io/v1beta1"
    command     = "aws"
    args        = ["eks", "get-token", "--cluster-name", module.eks.cluster_name, "--profile", "admin"]
  }
}

  data "aws_iam_user" "EKS_Admin_ID"{
    user_name = "admin"
  }

module "add_cluster_sg" {
  source      = "terraform-aws-modules/security-group/aws"
  version     = "~> 5.0"
  name        = "add_cluster_sg"
  description = "add_cluster_sg"

  vpc_id          = var.vpc_id
  use_name_prefix = false

  ingress_with_cidr_blocks = [
    {
      from_port   = 443
      to_port     = 443
      protocol    = "tcp"
      cidr_blocks = var.vpc_cidr_block
    }
  ]
  egress_with_cidr_blocks = [
    {
      from_port   = 0
      to_port     = 0
      protocol    = "-1"
      cidr_blocks = "0.0.0.0/0"
    }
  ]
}
module "add_node_sg" {
  source      = "terraform-aws-modules/security-group/aws"
  version     = "~> 5.0"
  name        = "add_node_sg"
  description = "add_node_sg"

  vpc_id          = var.vpc_id
  use_name_prefix = false

  ingress_with_cidr_blocks = [
    {
      from_port   = 0
      to_port     = 0
      protocol    = "-1"
      cidr_blocks = var.vpc_cidr_block
    }
  ]
  egress_with_cidr_blocks = [
    {
      from_port   = 0
      to_port     = 0
      protocol    = "-1"
      cidr_blocks = "0.0.0.0/0"
    }
  ]
}
```

## variable

```
variable "vpc_id" {
  type = string
}
variable "private_subnets" {
  type = list(string)
}
variable "vpc_cidr_block" {
  type = string
}
```


</ul>
-->

# 프로젝트를 하며 느낀 점
첫 팀 프로젝트를 진행하며 처음에는 어색함과 새로운 기술에 대한 낯설음 때문에 효율적으로 진행되지 않았습니다.<br>
하지만 회의를 통해 팀원들과 소통하고 역할을 명확히 분담하면서 프로젝트가 점점 개선되는 모습을 보며 성취감을 느낄 수 있었습니다.<br>
특히, 각자의 역할이 명확해지면서 서로의 강점을 활용하고 약점을 보완해나가는 과정을 통해 협업의 중요성을 깊이 깨닫게 되었고,<br>
원활한 소통이 프로젝트의 완성도와 효율성에 얼마나 큰 영향을 미치는지 실감할 수 있었습니다.<br>
이 경험은 분명 앞으로 다양한 팀과 협력하며 프로젝트를 진행하는데 좋은 밑거름이 될거라고 생각합니다.


