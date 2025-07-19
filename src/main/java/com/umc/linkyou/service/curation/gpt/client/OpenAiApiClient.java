package com.umc.linkyou.service.curation.gpt.client;

public interface OpenAiApiClient {
    String callGpt(String prompt);
}