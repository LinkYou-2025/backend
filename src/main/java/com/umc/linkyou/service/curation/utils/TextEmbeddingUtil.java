package com.umc.linkyou.service.curation.utils;

import java.util.*;
import java.util.stream.Collectors;

public class TextEmbeddingUtil {

    // 전체 코퍼스에서 단어별 TF-IDF 벡터 계산
    public static List<Map<String, Double>> computeTfidf(List<String> documents) {
        List<Map<String, Integer>> termFrequencies = new ArrayList<>();
        Map<String, Integer> docFrequencies = new HashMap<>();
        int totalDocs = documents.size();

        for (String doc : documents) {
            String[] tokens = tokenize(doc);
            Map<String, Integer> tf = new HashMap<>();
            Set<String> seen = new HashSet<>();
            for (String token : tokens) {
                tf.put(token, tf.getOrDefault(token, 0) + 1);
                if (!seen.contains(token)) {
                    docFrequencies.put(token, docFrequencies.getOrDefault(token, 0) + 1);
                    seen.add(token);
                }
            }
            termFrequencies.add(tf);
        }

        List<Map<String, Double>> tfidfVectors = new ArrayList<>();

        for (Map<String, Integer> tf : termFrequencies) {
            Map<String, Double> tfidf = new HashMap<>();
            for (String term : tf.keySet()) {
                double tfVal = tf.get(term);
                double idf = Math.log((double) totalDocs / (1 + docFrequencies.getOrDefault(term, 0)));
                tfidf.put(term, tfVal * idf);
            }
            tfidfVectors.add(tfidf);
        }

        return tfidfVectors;
    }

    public static double cosineSimilarity(Map<String, Double> vec1, Map<String, Double> vec2) {
        Set<String> allKeys = new HashSet<>(vec1.keySet());
        allKeys.addAll(vec2.keySet());

        double dot = 0.0, norm1 = 0.0, norm2 = 0.0;
        for (String key : allKeys) {
            double v1 = vec1.getOrDefault(key, 0.0);
            double v2 = vec2.getOrDefault(key, 0.0);
            dot += v1 * v2;
            norm1 += v1 * v1;
            norm2 += v2 * v2;
        }

        if (norm1 == 0.0 || norm2 == 0.0) return 0.0;
        return dot / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    private static String[] tokenize(String text) {
        return text.toLowerCase().replaceAll("[^\\p{L}\\p{N}\\s]", "").split("\\s+");
    }
}