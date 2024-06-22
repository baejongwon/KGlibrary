package com.kg.library;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.kg.library.Introduction.BookDTO;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;

@Service
public class MainService {

	// s3에 이미지 추가
	private String bucketName = "kglibrary"; // S3 버킷 이름
	private String s3FilePath = "static/img/"; // S3에 업로드할 경로

	@Autowired
	private S3Client s3Client; // AWS S3 클라이언트 주입

	private String getS3ObjectUri(String s3Key) {
		if (s3Key == null || s3Key.trim().isEmpty()) {
			// s3Key가 null 또는 빈 문자열인 경우에 대한 예외 처리
			return ""; // 또는 다른 기본값 설정
		}

		GetUrlRequest getUrlRequest = GetUrlRequest.builder().bucket(bucketName).key(s3Key).build();

		return s3Client.utilities().getUrl(getUrlRequest).toExternalForm();
	}

	private static final Logger logger = LoggerFactory.getLogger(MainService.class);

	public void hit_book(Model model) {

		try {

			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			ResponseEntity<ArrayList<BookDTO>> responseEntity = restTemplate.exchange(
					"https://www.bowfun.link/book/hit_book", HttpMethod.GET, new HttpEntity<>(headers),
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
//					String imageUrl = getS3ObjectUri(b.getImage());
//					imageUrls.add(imageUrl);
				}
			}
			// 모델에 데이터 추가
			model.addAttribute("hitbooks", hitbooks);
			model.addAttribute("imageUrls", imageUrls);

		} catch (HttpClientErrorException.NotFound notFoundException) {
			// 404 Not Found 에러 처리
			logger.error("Server returned 404 Not Found");
			// 다른 예외에 대한 처리도 추가 가능
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
					"https://www.bowfun.link/book/new_Book", HttpMethod.GET, new HttpEntity<>(headers),
					new ParameterizedTypeReference<ArrayList<BookDTO>>() {
					});

			ArrayList<BookDTO> newBooks = responseEntity.getBody();
			ArrayList<String> imageUrls = new ArrayList<>();
			
			if (newBooks != null) {
				for (BookDTO b : newBooks) {
					System.out.print("\u001B[31m");
					System.out.println("8086newbook Category: " + b.getCategory());
					System.out.println("8086newbook Image: " + b.getImage());
					System.out.println("8086newbook Title Info: " + b.getTitle_info());
					System.out.println("8086newbook Author Info: " + b.getAuthor_info());
					System.out.print("\u001B[0m");
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
			// 모델에 데이터 추가
			model.addAttribute("newBooks", newBooks);
			model.addAttribute("imageUrls", imageUrls);

		} catch (HttpClientErrorException.NotFound notFoundException) {
			// 404 Not Found 에러 처리
			logger.error("Server returned 404 Not Found");
			// 다른 예외에 대한 처리도 추가 가능
		} catch (Exception e) {
			// 기타 예외 처리
			logger.error("An error occurred while fetching data from the server", e);
		}

	}

//	public String search(String cp, Model model, String search2, String select) {
//		
//		try {
//			ResponseEntity<ArrayList<BookDTO>> responseEntity = new RestTemplate().exchange(
//					"http://www.bowfun.link/book/search", HttpMethod.GET, null,
//					new ParameterizedTypeReference<ArrayList<BookDTO>>() {
//					});
//
//			ArrayList<BookDTO> search = responseEntity.getBody();
//
//			if (search != null) {
//				for (BookDTO b : search) {
//					System.out.print("\u001B[31m");
//					System.out.println("8086newbook Category: " + b.getCategory());
//					System.out.println("8086newbook Image: " + b.getImage());
//					System.out.println("8086newbook Title Info: " + b.getTitle_info());
//					System.out.println("8086newbook Author Info: " + b.getAuthor_info());
//					System.out.print("\u001B[0m");
//					if (b.getImage() == null || b.getImage().trim().isEmpty()) {
//						b.setImage("20240109150111-40641325628.20230718121618.jpg");
//						continue;
//					}
//					if (b.getCategory().equals("API")) // API에서 받아온 이미지 라면
//					{
//						continue;
//					}
//				}
//			}
//			// 모델에 데이터 추가
//			model.addAttribute("newBooks", search);
//
//		} catch (HttpClientErrorException.NotFound notFoundException) {
//			// 404 Not Found 에러 처리
//			logger.error("Server returned 404 Not Found");
//			// 다른 예외에 대한 처리도 추가 가능
//		} catch (Exception e) {
//			// 기타 예외 처리
//			logger.error("An error occurred while fetching data from the server", e);
//		}
//		return null;
//	}
}
