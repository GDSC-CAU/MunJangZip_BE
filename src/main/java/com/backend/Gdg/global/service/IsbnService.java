package com.backend.Gdg.global.service;

import com.backend.Gdg.global.converter.IsbnConverter;
import com.backend.Gdg.global.domain.ExternalIsbnApiResponse;
import com.backend.Gdg.global.web.dto.IsbnResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Service
@Transactional
@RequiredArgsConstructor
public class IsbnService {

    private final RestTemplate restTemplate;
    private final IsbnConverter isbnConverter;

    // application.properties에 등록한 클라이언트 아이디와 시크릿
    @Value("${NAVER_API_ID}")
    private String clientId;

    @Value("${NAVER_API_SECRET}")
    private String clientSecret;

    // 네이버 책 상세 검색 API (XML 형식)
    private final String EXTERNAL_ISBN_API_URL = "https://openapi.naver.com/v1/search/book_adv.xml?d_isbn={isbn}&display=10&start=1";

    public IsbnResponseDto fetchIsbnInfo(String isbn) {
        // HTTP 헤더 설정: 클라이언트 아이디와 시크릿 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", clientId);
        headers.add("X-Naver-Client-Secret", clientSecret);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // GET 요청을 통해 XML 응답 문자열 획득
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                EXTERNAL_ISBN_API_URL,
                HttpMethod.GET,
                entity,
                String.class,
                isbn
        );

        String xmlResponse = responseEntity.getBody();

        try {
            // XML 파싱
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xmlResponse.getBytes(StandardCharsets.UTF_8)));

            // 첫 번째 <item> 요소에서 책 정보를 추출
            NodeList itemList = doc.getElementsByTagName("item");
            if (itemList.getLength() == 0) {
                throw new RuntimeException("검색 결과가 없습니다.");
            }
            Element itemElement = (Element) itemList.item(0);

            String title = getTagValue("title", itemElement);
            String author = getTagValue("author", itemElement);
            String image = getTagValue("image", itemElement);
            String isbnValue = getTagValue("isbn", itemElement);

            // 외부 API 응답 모델에 값 설정
            ExternalIsbnApiResponse externalResponse = new ExternalIsbnApiResponse();
            externalResponse.setTitle(title);
            externalResponse.setAuthor(author);
            externalResponse.setCoverImageUrl(image);
            externalResponse.setIsbn(isbnValue);

            // 컨버터를 통해 내부 DTO로 변환 후 반환
            return isbnConverter.toDto(externalResponse);
        } catch (Exception e) {
            throw new RuntimeException("XML 파싱 또는 API 응답 처리 중 오류 발생", e);
        }
    }

    // XML Element에서 특정 태그 값을 추출하는 유틸리티 메서드
    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }
}
