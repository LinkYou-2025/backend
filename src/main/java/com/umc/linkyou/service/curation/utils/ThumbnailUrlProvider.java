package com.umc.linkyou.service.curation.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.YearMonth;

@Component
public class ThumbnailUrlProvider {

    private final String baseUrl;
    private static final String EXTENSION = ".png";

    public ThumbnailUrlProvider(@Value("${cloud.aws.s3.base-url}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUrlForMonth(String folder, String month) {
        String monthPart = month.split("-")[1];
        return String.format("%s/%s/%s%s", baseUrl, folder, monthPart, EXTENSION);
    }

    public String getUrlForMonth(String folder, YearMonth yearMonth) {
        String monthPart = String.format("%02d", yearMonth.getMonthValue());
        return String.format("%s/%s/%s%s", baseUrl, folder, monthPart, EXTENSION);
    }
}
