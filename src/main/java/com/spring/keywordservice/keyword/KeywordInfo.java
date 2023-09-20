package com.spring.keywordservice.keyword;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KeywordInfo {
    private String relKeyword;              // 연관 키워드
    private Object monthlyPcQcCnt;          // 월간 PC 검색수
    private Object monthlyMobileQcCnt;      // 월간 Mobile 검색수
    private Object monthlyAvePcClkCnt;      // 월 평균 PC 클릭수
    private Object monthlyAveMobileClkCnt;  // 월 평균 Mobile 클릭수
    private Object monthlyAvePcCtr;         // 월 평균 PC 클릭률
    private Object monthlyAveMobileCtr;     // 월 평균 Mobile 클릭률
    private Object plAvgDepth;              // 월 평균 노출 광고수
    private Object compIdx;                 // PC광고 경쟁 정도
}
