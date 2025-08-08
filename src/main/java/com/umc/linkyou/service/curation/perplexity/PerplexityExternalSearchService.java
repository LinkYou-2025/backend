package com.umc.linkyou.service.curation.perplexity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.linkyou.service.curation.perplexity.client.PerplexityApiClient;
import com.umc.linkyou.web.dto.curation.RecommendedLinkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerplexityExternalSearchService {

    private final PerplexityApiClient perplexityApiClient;
    private final ObjectMapper objectMapper;

    public List<RecommendedLinkResponse> searchExternalLinks(
            List<String> recentUrls,
            List<String> tagNames,
            int limit,
            String jobName,
            String gender
    ) {
        // 1) 시스템 프롬프트(고정 규칙) — 여기서만 관리
        String systemPrompt = """
        You are a WEB SEARCH assistant for personalized content curation.
        
        AUDIENCE PROFILE:
        - Job (primary): %s
        - Gender: %s
        - Locale: Korea (KR), language: Korean
        
        TARGETING RULES (Very Important):
        - Optimize for the user's *job context*: tasks, tools, workflows, skill growth, portfolio/career relevance.
        - Calibrate *difficulty*: beginner/intermediate/professional depending on common needs of the given job (prefer actionable and recent know-how).
        - Consider gender only to avoid unsafe/inappropriate content; DO NOT stereotype interests by gender.
        
        QUALITY / SAFETY RULES:
        - Return ONLY a JSON array, no prose/markdown/code fences.
        - Exactly %d items.
        - Each item: {"title":"...", "url":"..."} (both non-empty).
        - URL must be publicly reachable now (HTTP/HTTPS; no 404/401/502).
        - Prefer reputable Korean sources; avoid login/paywalls/spam/clickbait/aggregators.
        - Prefer content published/updated within the last 24 months unless clearly evergreen.
        - Exclude NSFW, gambling, high-risk financial advice, medical claims without reputable sources.
        
        DIVERSITY & RELEVANCE:
        - Cover a *diverse set of domains* (avoid many results from the same site).
        - Maximize *topical relevance* to the user's tags and job. If a conflict, job relevance wins.
        - Titles should reflect practical value (guide, checklist, tutorial, case study, trend report).
        
        OUTPUT: JSON array only.
        """.formatted(
                safe(jobName),
                safe(gender),
                limit
        );

        // 2) 유저 프롬프트(동적 데이터)
        String userPrompt = """
        다음은 사용자가 최근 본 링크(절대 재사용 금지):
        %s
        
        사용자 중요 태그: %s
        
        요구사항:
        - 위 태그와 직무(%s)에 직결되는 주제 위주로, 실제 존재하는 공개 웹페이지를 정확히 %d개 추천.
        - 실무 적용 가능성 높은 콘텐츠(튜토리얼/체크리스트/가이드/트렌드 요약/사례연구) 선호.
        - 제목은 과장/낚시성 표현을 피하고 핵심 주제를 명확히 드러내는 자료만.
        
        형식: [{"title":"...","url":"..."}]
        """.formatted(
                String.join("\n", recentUrls),
                (tagNames == null || tagNames.isEmpty()) ? "(없음)" : String.join(", ", tagNames),
                safe(jobName),
                limit
        );

        // 3) Perplexity 호출 (클라이언트는 전달만)
        var messages = List.of(
                Map.of("role", "system", "content", systemPrompt),
                Map.of("role", "user",   "content", userPrompt)
        );
        String content = perplexityApiClient.chat(messages);
        if (content == null) return List.of();

        // 4) JSON 배열 파싱
        String json = extractJsonArray(content);
        List<Map<String, String>> parsed;
        try {
            parsed = objectMapper.readValue(json, new TypeReference<>() {});
            System.out.println("🔍 jobName = " + jobName + ", gender = " + gender);
        } catch (Exception e) {
            System.out.println("❌ Perplexity 추천 JSON 파싱 실패: " + e.getMessage());
            return List.of();
        }

        // 5) 내부 URL 정규화 후 중복 기준
        Set<String> internalCanon = recentUrls.stream()
                .map(this::canonicalize).collect(Collectors.toSet());

        // 6) 유효성 검사 + 중복 제거 + 결과 조립
        List<RecommendedLinkResponse> out = new ArrayList<>();
        Set<String> seen = new HashSet<>(internalCanon);
        Set<String> seenTitles = new HashSet<>();

        for (Map<String, String> m : parsed) {
            String url = m.getOrDefault("url", "").trim();
            String title = m.getOrDefault("title", "").trim();
            if (url.isBlank() || title.isBlank()) continue;

            String canon = canonicalize(url);
            if (seen.contains(canon)) continue;

            if (!isReachable(url)) continue; // HEAD→GET 검증

            String normTitle = normalizeTitle(title);
            if (seenTitles.contains(normTitle)) continue;
            seenTitles.add(normTitle);

            out.add(RecommendedLinkResponse.builder()
                    .title(title)
                    .url(url)
                    .userLinkuId(null)
                    .domain(null)
                    .domainImageUrl(null)
                    .imageUrl(null)
                    .categories(null)
                    .build());
            seen.add(canon);

            if (out.size() == limit) break;
        }

        return out;
    }

    private String safe(String s) {
        return (s == null || s.isBlank()) ? "unknown" : s;
    }

    // --- 유틸: URL 접근성 검사 (HEAD 실패 시 작은 GET 재시도) ---
    private boolean isReachable(String url) {
        try {
            HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
            c.setInstanceFollowRedirects(true);
            c.setRequestMethod("HEAD");
            c.setRequestProperty("User-Agent", "LinkU-Bot/1.0");
            c.setConnectTimeout(3000);
            c.setReadTimeout(3000);
            int code = c.getResponseCode();
            if (code >= 200 && code < 400) return true;

            c = (HttpURLConnection) new URL(url).openConnection();
            c.setInstanceFollowRedirects(true);
            c.setRequestMethod("GET");
            c.setRequestProperty("User-Agent", "LinkU-Bot/1.0");
            c.setRequestProperty("Range", "bytes=0-1024");
            c.setConnectTimeout(3000);
            c.setReadTimeout(3000);
            code = c.getResponseCode();
            return (code >= 200 && code < 400);
        } catch (Exception e) {
            return false;
        }
    }

    // --- 유틸: URL 정규화 ---
    private String canonicalize(String u) {
        String x = u.replaceAll("#.*$", "").replaceAll("\\?.*$", "");
        x = x.replace("://www.", "://");
        if (x.endsWith("/")) x = x.substring(0, x.length() - 1);
        return x;
    }

    // --- 유틸: JSON 배열만 추출 ---
    private String extractJsonArray(String response) {
        if (response == null) return "[]";
        String cleaned = response.replaceAll("(?s)```json", "")
                .replaceAll("(?s)```", "")
                .trim();
        int s = cleaned.indexOf("[");
        int e = cleaned.lastIndexOf("]") + 1;
        return (s != -1 && e > s) ? cleaned.substring(s, e) : "[]";
    }

    private String normalizeTitle(String t) {
        return t.trim().replaceAll("\\s+", " ").toLowerCase(Locale.ROOT);
    }
}
