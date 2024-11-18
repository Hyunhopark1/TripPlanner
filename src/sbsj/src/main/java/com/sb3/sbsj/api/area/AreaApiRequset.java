package com.sb3.sbsj.api.area;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class AreaApiRequset {
    @Autowired
    private ObjectMapper objectMapper; // JSON 파싱을 위한 ObjectMapper

    @Autowired
    private AreaApiServiceImpl areaApiService;

    @Value("${Api.key}")
    String serviceKey;

    public AreaApiResponse fetchDataFromApi() throws Exception {
        // 변수 선언
        String baseUrl = "https://apis.data.go.kr/B551011/KorService1/areaCode1";
        int numOfRows = 20;
        String mobileOS = "ETC";
        String mobileApp = "test";
        String responseType = "json";

        // URL 구성
        String url = String.format(
                "%s?numOfRows=%d&MobileOS=%s&MobileApp=%s&_type=%s&serviceKey=%s",
                baseUrl, numOfRows, mobileOS, mobileApp, responseType, serviceKey
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        AreaApiResponse areaApiResponse = objectMapper.readValue(response.body(), AreaApiResponse.class);

        // DTO를 사용해 데이터베이스에 저장
        areaApiService.saveAreasFromApiResponse(areaApiResponse);

        // api 응답
        return areaApiResponse;
    }

}