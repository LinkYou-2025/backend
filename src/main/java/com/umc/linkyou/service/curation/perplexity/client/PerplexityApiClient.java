package com.umc.linkyou.service.curation.perplexity.client;

import java.util.List;
import java.util.Map;

public interface PerplexityApiClient {
    String chat(List<Map<String, String>> messages);
}