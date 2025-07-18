package com.umc.linkyou.openApi;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

/**
 * 웹페이지에서 텍스트 본문을 추출하는 유틸 클래스
 */
@Slf4j
@Component
public class WebContentExtractor {

    public String extractTextFromUrl(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(5000)
                    .get();

            // 우선순위 1: <article>
            Elements article = doc.select("article");
            if (!article.isEmpty()) return article.text();

            // 우선순위 2: <main>
            Elements main = doc.select("main");
            if (!main.isEmpty()) return main.text();

            // 우선순위 3: class/id에 "content", "body" 포함
            Elements content = doc.select("[class*=content], [id*=content], [class*=body], [id*=body]");
            if (!content.isEmpty()) return content.text();

            // fallback: 전체 body 텍스트
            return doc.body().text();

        } catch (Exception e) {
            log.error("[크롤링 실패] URL: {}, 이유: {}", url, e.getMessage());
            return null;
        }
    }
}
