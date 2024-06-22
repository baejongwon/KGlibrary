package com.kg.library.Introduction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.kg.library.Introduction.BookDTO;
import com.kg.library.Introduction.BookService;
import com.kg.library.Introduction.IBookMapper;

import edu.emory.mathcs.backport.java.util.Collections;

@Service
public class Introduction_Service {
	
	@Autowired
	private IBookMapper mapper;
	 @Autowired
	private JdbcTemplate jdbcTemplate;
	private static final Logger logger = LoggerFactory.getLogger(BookService.class);
	
//	public List<Map<String, Object>> dataStatus(BookDTO board) {
//		
//		//String query = mapper.dataStatus(board);
//		String query = "SELECT category, COUNT(*) as bookCount, SUM(COUNT(*)) OVER() as totalBookCount FROM book GROUP BY category";
//		List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
//		//ArrayList<BookDTO> dataStatus = mapper.dataStatus(board);
//		
//		for (Map<String, Object> row : result) {
//			System.out.println("Category: " + row.get("category"));
//	        System.out.println("Book Count: " + row.get("bookCount"));
//	        System.out.println("Total Book Count: " + row.get("totalBookCount"));
//			}	
//		 return result;
//	}
	 
	 public List<Map<String, Object>> dataStatus(BookDTO board) {
			
		 try {
			 RestTemplate restTemplate = new RestTemplate();

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				
			 	ResponseEntity<List<Map<String, Object>>> responseEntity = restTemplate.exchange(
	                    "https://www.bowfun.link/book/dataStatus",
	                    HttpMethod.GET,
	                    new HttpEntity<>(headers),
	                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
	            );

		 	 List<Map<String, Object>> dataStatus = responseEntity.getBody();
		 	 System.out.println("dataStatus 요청 보냄");
		 	 
		 	 for(Map<String, Object> b : dataStatus) {
		 		 System.out.println("data 정보 받는 부분: "+ dataStatus);
		 	 }
			 	 return dataStatus;
	            
	        } catch (HttpClientErrorException.NotFound notFoundException) {
	            // 404 Not Found 에러 처리
	            logger.error("Server returned 404 Not Found");
	            // 다른 예외에 대한 처리도 추가 가능
	            return Collections.emptyList(); // 빈 리스트 반환
	        } catch (Exception e) {
	            // 기타 예외 처리
	            logger.error("An error occurred while fetching data from the server", e);
	            return Collections.emptyList(); // 빈 리스트 반환
	        }
	 
		}
	}
	
