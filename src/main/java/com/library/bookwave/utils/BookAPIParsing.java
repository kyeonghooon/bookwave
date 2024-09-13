package com.library.bookwave.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.sql.Date;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.library.bookwave.dto.bookParsing.Book;
import com.library.bookwave.dto.bookParsing.Item;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

/**
 * 최초 DB 샘플 데이터 구축을 위한 파싱용 유팅클래스
 */
public class BookAPIParsing {
	
//	public static void main(String[] args) {
//		parseBooksFromAPI();
//	}

	// API를 통해 책 정보를 파싱하고 SQL 쿼리를 파일로 저장하는 메서드
	private static void parseBooksFromAPI() {
		PrintWriter writer = null;

		try {
			// SQL 쿼리를 기록할 파일을 생성하고 PrintWriter로 파일에 쓸 준비를 함
			FileWriter fileWriter = new FileWriter("books.sql");
			writer = new PrintWriter(fileWriter);
			
			// API 호출을 반복하여 여러 페이지의 책 정보를 가져옴
			for (int i = 0; i < 20; i++) {
				// URI 생성: API 호출을 위한 URL 및 쿼리 파라미터 설정
				URI uri = UriComponentsBuilder.fromUriString("http://www.aladin.co.kr")
						.path("/ttb/api/ItemList.aspx")
						.queryParam("ttbkey", "ttbkred7300361331001")
						.queryParam("QueryType", "Bestseller")
						.queryParam("MaxResults", "50")
						.queryParam("start", i + 1)
						.queryParam("SearchTarget", "Book")
						.queryParam("output", "js")
						.queryParam("Cover", "Big")
						.queryParam("Version", "20131101")
						.build()
						.toUri();

				// RestTemplate을 사용하여 API 요청을 보냄
				RestTemplate restTemplate1 = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/json");
				MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
				HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

				// API 호출 및 응답 처리
				ResponseEntity<Book> response = restTemplate1.exchange(uri, HttpMethod.GET, requestEntity, Book.class);
				Book book = response.getBody();
				
				// 응답에서 책 정보를 추출하고 SQL 쿼리를 생성하여 파일에 기록
				if (book != null && book.getItem() != null) {
					for (Item item : book.getItem()) {
						// 날짜 Format
						Date publishDate = item.getPubDate();
                        String formattedDate = (publishDate != null) ? publishDate.toString() : "NULL";
                        
						String sql = String.format(
							"insert into book_tb (title, description, author, publisher, cover, category, publish_date, total_stock, current_stock) values ('%s', '%s', '%s', '%s', '%s', '%s', '%s', 1, 1);",
							item.getTitle().replace("'", "''"),
							item.getDescription().replace("'", "''"),
							item.getAuthor().replace("'", "''"),
							item.getPublisher().replace("'", "''"),
							item.getCover().replace("'", "''"),
							item.getCategoryName().replace("'", "''"),
							formattedDate
						);
						// SQL 쿼리를 파일에 기록
						writer.println(sql); 
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
}
