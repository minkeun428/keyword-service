package com.spring.keywordservice.keyword;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class KeywordController {
    private final KeywordService keywordService;

    public KeywordController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @GetMapping("/search-keyword")
    public String getSearchKeyword(@RequestParam String keyword, Model model) {
        List<KeywordInfo> keywordList = this.keywordService.selectKeywordInfo(keyword);
        if (keywordList.isEmpty()) {
            log.error("Search Keyword List Is Empty!! :{}", keyword);
            return "error";
        }

        model.addAttribute("keywordList", keywordList);
        return "result";
    }
}
