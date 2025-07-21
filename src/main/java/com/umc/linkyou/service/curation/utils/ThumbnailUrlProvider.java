package com.umc.linkyou.service.curation.utils;

import java.time.YearMonth;

public class ThumbnailUrlProvider {

    private static final String BASE_URL = "https://linku-image-bucket.s3.ap-southeast-2.amazonaws.com/curation/";
    private static final String EXTENSION = ".png";

    public static String getUrlForMonth(String month) {
        // 예: "2025-04" → "04"
        String monthPart = month.split("-")[1];
        return BASE_URL + monthPart + EXTENSION;
    }

    public static String getUrlForMonth(YearMonth yearMonth) {
        String monthPart = String.format("%02d", yearMonth.getMonthValue());
        return BASE_URL + monthPart + EXTENSION;
    }
}
