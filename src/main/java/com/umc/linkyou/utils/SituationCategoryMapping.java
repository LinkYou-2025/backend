package com.umc.linkyou.utils;

import java.util.List;
import java.util.Map;

public class SituationCategoryMapping {

    private static final Map<Long, List<Long>> situationToCategoryMap = Map.ofEntries(
            // 고등학생 (job_id = 1)
            Map.entry(1L, List.of(1L, 14L, 11L)),  // 통학 중 → 어학, 영상·뮤직, 에세이·칼럼
            Map.entry(2L, List.of(3L, 5L, 10L)),   // 공부 중 → 공부법, 자기계발, 심리·자기이해
            Map.entry(3L, List.of(14L, 15L)),      // 식사 중 → 영상·뮤직, 맛집·여행
            Map.entry(4L, List.of(3L, 1L, 10L)),  // 시험 준비 → 공부법, 어학, 심리·자기이해
            Map.entry(5L, List.of(14L, 12L)),      // 친구랑 → 영상·뮤직, 트렌드
            Map.entry(6L, List.of(12L, 9L)),       // 쇼핑 중 → 트렌드, 라이프스타일
            Map.entry(7L, List.of(11L, 10L, 14L)),// 휴식 중 → 에세이·칼럼, 심리·자기이해, 영상·뮤직
            Map.entry(8L, List.of(11L, 10L)),      // 자기 전 → 에세이·칼럼, 심리·자기이해

            // 대학생 (job_id = 2)
            Map.entry(9L, List.of(8L, 3L, 4L)),    // 과제 중 → 생산성·툴, 공부법, IT·개발
            Map.entry(10L, List.of(1L, 2L, 14L)),  // 통학 중 → 어학, 뉴스, 영상·뮤직
            Map.entry(11L, List.of(12L, 9L)),      // 쇼핑 중 → 트렌드, 라이프스타일
            Map.entry(12L, List.of(5L, 7L)),       // 알바 중 → 자기계발, 비즈니스 인사이트
            Map.entry(13L, List.of(12L, 2L, 13L)), // 트렌드 확인 → 트렌드, 뉴스, 디자인·예술
            Map.entry(14L, List.of(15L, 14L)),     // 데이트 중 → 맛집·여행, 영상·뮤직
            Map.entry(15L, List.of(11L, 10L)),     // 휴식 중 → 에세이·칼럼, 심리·자기이해
            Map.entry(16L, List.of(11L, 14L)),     // 자기 전 → 에세이·칼럼, 영상·뮤직

            // 직장인 (job_id = 3)
            Map.entry(17L, List.of(2L, 14L, 12L)),   // 출퇴근 → 뉴스, 영상·뮤직, 트렌드
            Map.entry(18L, List.of(12L, 7L, 4L)),    // 트렌드 확인 → 트렌드, 비즈니스 인사이트, IT·개발
            Map.entry(19L, List.of(8L, 7L)),         // 업무 중 → 생산성·툴, 비즈니스 인사이트
            Map.entry(20L, List.of(6L, 5L, 10L)),    // 커리어 고민 → 취업·이직, 자기계발, 심리·자기이해
            Map.entry(21L, List.of(12L, 9L)),        // 쇼핑 중 → 트렌드, 라이프스타일
            Map.entry(22L, List.of(15L, 14L)),       // 데이트 중 → 맛집·여행, 영상·뮤직
            Map.entry(23L, List.of(10L, 11L)),       // 휴식 중 → 심리·자기이해, 에세이·칼럼
            Map.entry(24L, List.of(11L, 14L)),       // 자기 전 → 에세이·칼럼, 영상·뮤직

            // 자영업자 (job_id = 4)
            Map.entry(25L, List.of(2L, 12L)),         // 출퇴근 → 뉴스, 트렌드
            Map.entry(26L, List.of(8L, 7L)),          // 업무 준비 중 → 생산성·툴, 비즈니스 인사이트
            Map.entry(27L, List.of(15L, 14L)),        // 데이트 중 → 맛집·여행, 영상·뮤직
            Map.entry(28L, List.of(15L, 9L)),         // 식사 → 맛집·여행, 라이프스타일
            Map.entry(29L, List.of(12L, 9L)),         // 쇼핑 중 → 트렌드, 라이프스타일
            Map.entry(30L, List.of(12L, 2L, 13L)),    // 트렌드 확인 → 트렌드, 뉴스, 디자인·예술
            Map.entry(31L, List.of(11L, 10L)),        // 휴식 중 → 심리·자기이해, 에세이·칼럼
            Map.entry(32L, List.of(11L, 14L)),        // 자기 전 → 에세이·칼럼, 영상·뮤직

            // 프리랜서 (job_id = 5)
            Map.entry(33L, List.of(8L, 13L)),         // 작업 중 → 생산성·툴, 디자인·예술
            Map.entry(34L, List.of(12L, 9L)),          // 쇼핑 중 → 트렌드, 라이프스타일
            Map.entry(35L, List.of(12L, 13L, 4L)),    // 트렌드 확인 → 트렌드, 디자인·예술, IT·개발
            Map.entry(36L, List.of(15L, 14L)),         // 데이트 중 → 맛집·여행, 영상·뮤직
            Map.entry(37L, List.of(9L, 14L)),          // 운동 중 → 라이프스타일, 영상·뮤직
            Map.entry(38L, List.of(15L)),               // 식사 → 맛집·여행
            Map.entry(39L, List.of(11L, 10L)),         // 휴식 중 → 에세이·칼럼, 심리·자기이해
            Map.entry(40L, List.of(14L, 10L)),         // 자기 전 → 영상·뮤직, 심리·자기이해

            // 취준생 (job_id = 6)
            Map.entry(41L, List.of(6L, 5L)),             // 자소서 작성 → 취업·이직, 자기계발
            Map.entry(42L, List.of(6L, 10L)),            // 면접 준비 → 취업·이직, 심리·자기이해
            Map.entry(43L, List.of(9L, 14L)),            // 요리 중 → 라이프스타일, 영상·뮤직
            Map.entry(44L, List.of(12L, 2L, 7L)),       // 트렌드 확인 → 트렌드, 뉴스, 비즈니스 인사이트
            Map.entry(45L, List.of(12L, 9L)),            // 쇼핑 중 → 트렌드, 라이프스타일
            Map.entry(46L, List.of(14L, 9L)),            // 운동 중 → 영상·뮤직, 라이프스타일
            Map.entry(47L, List.of(11L, 10L)),           // 휴식 중 → 에세이·칼럼, 심리·자기이해
            Map.entry(48L, List.of(10L, 14L))            // 자기 전 → 심리·자기이해, 영상·뮤직
    );

    /**
     * 상황ID로 추천 카테고리 목록 반환
     * @param situationId 상황 ID
     * @return 카테고리 ID 리스트, 없으면 빈 리스트 반환
     */
    public static List<Long> getCategoriesForSituation(Long situationId) {
        return situationToCategoryMap.getOrDefault(situationId, List.of());
    }
}

