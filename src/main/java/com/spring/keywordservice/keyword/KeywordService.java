package com.spring.keywordservice.keyword;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.keywordservice.utils.Signatures;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class KeywordService {
    @Value("${CUSTOMER_ID}")
    private String customerId;
    @Value("${API_KEY}")
    private String accessKey;
    @Value("${SECRET_KEY}")
    private String secretKey;
    @Value("${BASE_URL}")
    private String baseUrl;
    private final String path = "/keywordstool";
    private final String parameter1 = "hintKeywords=";
    private final String parameter2 = "showDetail=";

    public List<KeywordInfo> selectKeywordInfo(String keyword) {
        long timeStamp = System.currentTimeMillis();
        String times = String.valueOf(timeStamp);

        try {
            keyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
            //keyword = URLEncoder.encode(keyword, "EUC-KR");
        } catch (Exception e) {
            throw new RuntimeException("인코딩 실패.");
        }

        try {
            URL url = new URL(baseUrl + path + "?" + parameter1 + keyword + "&" +  parameter2 + "1");
//            URL url = new URL(baseUrl + path + "?" + parameter1 + keyword);
            String signature = Signatures.of(times, "GET", path, secretKey);
            System.out.println("url = " + url);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Timestamp", times);
            con.setRequestProperty("X-API-KEY", accessKey);
            con.setRequestProperty("X-Customer", customerId);
            con.setRequestProperty("X-Signature", signature);
            con.setDoOutput(true);

            int responseCode = con.getResponseCode();
            BufferedReader br;
            System.out.println("responseCode : " + responseCode);

            if(responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream(), StandardCharsets.UTF_8));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();

            while((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }

            br.close();
            System.out.println("Response : " + response);

            try {
                ObjectMapper mapper = new ObjectMapper();

                return mapper.readValue(
                        mapper.readTree(response.toString()).get("keywordList").toString(),
                        new TypeReference<List<KeywordInfo>>() {}
                );
            } catch (Exception e) {
                System.out.println("e = " + e);
            }

        } catch (Exception e) {
            System.out.println("Wrong URL.");
        }

        return new ArrayList();
    }
}
