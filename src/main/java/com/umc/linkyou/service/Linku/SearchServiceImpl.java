package com.umc.linkyou.service.Linku;

import com.umc.linkyou.web.dto.QuickSearchDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    public QuickSearchDto.QuickSearchResult quickSearch(String keyword, Long userId) {
        return QuickSearchDto.QuickSearchResult.builder()
                .searchKeyword(keyword)
                .recentKeywords(List.of("오픽", "드라이브", "취준생"))
                .links(List.of(
                        QuickSearchDto.LinkDetailDto.builder()
                                .title("오픽 일주일만에 AL 받는 공부법")
                                .summary("오늘은 일주일만에 오픽 AL 받는 공부법에 대해 알아보겠습니다...")
                                .source("NAVER 블로그")
                                .url("https://blog.naver.com/example1")
                                .favicon("https://blogimgs.naver.net/favicon.ico")
                                .build(),
                        QuickSearchDto.LinkDetailDto.builder()
                                .title("이렇게만 하면 당신도 오픽 끝낼 수 있다!")
                                .summary("오픽시험에서 대화의 흐름을 유지, 발음과 억양 주의, 즐겁게 말하는 팁...")
                                .source("NAVER 블로그")
                                .url("https://blog.naver.com/example2")
                                .favicon("https://blogimgs.naver.net/favicon.ico")
                                .build(),
                        QuickSearchDto.LinkDetailDto.builder()
                                .title("취준생, 꼭 따야하는 자격증 top 3")
                                .summary("취준생이 꼭 따야하는 자격증에는 오픽, 토익, 토스가 있다. 그 이유는...")
                                .source("NAVER 포스트")
                                .url("https://post.naver.com/example3")
                                .favicon("https://postfiles.pstatic.net/favicon.ico")
                                .build()
                ))
                .build();
    }
}
