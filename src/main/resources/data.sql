INSERT INTO category (category_id, color_code, name) VALUES (1, '#000000', '어학')
    ON DUPLICATE KEY UPDATE name = VALUES(name), color_code = VALUES(color_code);
INSERT INTO category (category_id, color_code, name) VALUES (2, '#000000', '뉴스')
    ON DUPLICATE KEY UPDATE name = VALUES(name), color_code = VALUES(color_code);
INSERT INTO category (category_id, color_code, name) VALUES (3, '#000000', '공부법')
    ON DUPLICATE KEY UPDATE name = VALUES(name), color_code = VALUES(color_code);
INSERT INTO category (category_id, color_code, name) VALUES (4, '#000000', 'IT·개발')
    ON DUPLICATE KEY UPDATE name = VALUES(name), color_code = VALUES(color_code);
INSERT INTO category (category_id, color_code, name) VALUES (5, '#000000', '자기계발')
    ON DUPLICATE KEY UPDATE name = VALUES(name), color_code = VALUES(color_code);
INSERT INTO category (category_id, color_code, name) VALUES (6, '#000000', '취업·이직')
    ON DUPLICATE KEY UPDATE name = VALUES(name), color_code = VALUES(color_code);
INSERT INTO category (category_id, color_code, name) VALUES (7, '#000000', '비즈니스 인사이트')
    ON DUPLICATE KEY UPDATE name = VALUES(name), color_code = VALUES(color_code);
INSERT INTO category (category_id, color_code, name) VALUES (8, '#000000', '생산성·툴')
    ON DUPLICATE KEY UPDATE name = VALUES(name), color_code = VALUES(color_code);
INSERT INTO category (category_id, color_code, name) VALUES (9, '#000000', '라이프스타일')
    ON DUPLICATE KEY UPDATE name = VALUES(name), color_code = VALUES(color_code);
INSERT INTO category (category_id, color_code, name) VALUES (10, '#000000', '심리·자기이해')
    ON DUPLICATE KEY UPDATE name = VALUES(name), color_code = VALUES(color_code);
INSERT INTO category (category_id, color_code, name) VALUES (11, '#000000', '에세이·칼럼')
    ON DUPLICATE KEY UPDATE name = VALUES(name), color_code = VALUES(color_code);
INSERT INTO category (category_id, color_code, name) VALUES (12, '#000000', '트렌드')
    ON DUPLICATE KEY UPDATE name = VALUES(name), color_code = VALUES(color_code);
INSERT INTO category (category_id, color_code, name) VALUES (13, '#000000', '디자인·예술')
    ON DUPLICATE KEY UPDATE name = VALUES(name), color_code = VALUES(color_code);
INSERT INTO category (category_id, color_code, name) VALUES (14, '#000000', '영상·뮤직')
    ON DUPLICATE KEY UPDATE name = VALUES(name), color_code = VALUES(color_code);
INSERT INTO category (category_id, color_code, name) VALUES (15, '#000000', '맛집·여행')
    ON DUPLICATE KEY UPDATE name = VALUES(name), color_code = VALUES(color_code);
INSERT INTO category (category_id, color_code, name) VALUES (16, '#000000', '기타')
    ON DUPLICATE KEY UPDATE name = VALUES(name), color_code = VALUES(color_code);

INSERT INTO folder (folder_id, category_id, folder_name, parent_folder_id, created_at, updated_at)
VALUES (1, 1, '어학', NULL, NOW(), NOW())
    ON DUPLICATE KEY UPDATE folder_name = VALUES(folder_name), category_id = VALUES(category_id);

INSERT INTO folder (folder_id, category_id, folder_name, parent_folder_id, created_at, updated_at)
VALUES (2, 2, '뉴스', NULL, NOW(), NOW())
    ON DUPLICATE KEY UPDATE folder_name = VALUES(folder_name), category_id = VALUES(category_id);

INSERT INTO folder (folder_id, category_id, folder_name, parent_folder_id, created_at, updated_at)
VALUES (3, 3, '공부법', NULL, NOW(), NOW())
    ON DUPLICATE KEY UPDATE folder_name = VALUES(folder_name), category_id = VALUES(category_id);

INSERT INTO folder (folder_id, category_id, folder_name, parent_folder_id, created_at, updated_at)
VALUES (4, 4, 'IT·개발', NULL, NOW(), NOW())
    ON DUPLICATE KEY UPDATE folder_name = VALUES(folder_name), category_id = VALUES(category_id);

INSERT INTO folder (folder_id, category_id, folder_name, parent_folder_id, created_at, updated_at)
VALUES (5, 5, '자기계발', NULL, NOW(), NOW())
    ON DUPLICATE KEY UPDATE folder_name = VALUES(folder_name), category_id = VALUES(category_id);

INSERT INTO folder (folder_id, category_id, folder_name, parent_folder_id, created_at, updated_at)
VALUES (6, 6, '취업·이직', NULL, NOW(), NOW())
    ON DUPLICATE KEY UPDATE folder_name = VALUES(folder_name), category_id = VALUES(category_id);

INSERT INTO folder (folder_id, category_id, folder_name, parent_folder_id, created_at, updated_at)
VALUES (7, 7, '비즈니스 인사이트', NULL, NOW(), NOW())
    ON DUPLICATE KEY UPDATE folder_name = VALUES(folder_name), category_id = VALUES(category_id);

INSERT INTO folder (folder_id, category_id, folder_name, parent_folder_id, created_at, updated_at)
VALUES (8, 8, '생산성·툴', NULL, NOW(), NOW())
    ON DUPLICATE KEY UPDATE folder_name = VALUES(folder_name), category_id = VALUES(category_id);

INSERT INTO folder (folder_id, category_id, folder_name, parent_folder_id, created_at, updated_at)
VALUES (9, 9, '라이프스타일', NULL, NOW(), NOW())
    ON DUPLICATE KEY UPDATE folder_name = VALUES(folder_name), category_id = VALUES(category_id);

INSERT INTO folder (folder_id, category_id, folder_name, parent_folder_id, created_at, updated_at)
VALUES (10, 10, '심리·자기이해', NULL, NOW(), NOW())
    ON DUPLICATE KEY UPDATE folder_name = VALUES(folder_name), category_id = VALUES(category_id);

INSERT INTO folder (folder_id, category_id, folder_name, parent_folder_id, created_at, updated_at)
VALUES (11, 11, '에세이·칼럼', NULL, NOW(), NOW())
    ON DUPLICATE KEY UPDATE folder_name = VALUES(folder_name), category_id = VALUES(category_id);

INSERT INTO folder (folder_id, category_id, folder_name, parent_folder_id, created_at, updated_at)
VALUES (12, 12, '트렌드', NULL, NOW(), NOW())
    ON DUPLICATE KEY UPDATE folder_name = VALUES(folder_name), category_id = VALUES(category_id);

INSERT INTO folder (folder_id, category_id, folder_name, parent_folder_id, created_at, updated_at)
VALUES (13, 13, '디자인·예술', NULL, NOW(), NOW())
    ON DUPLICATE KEY UPDATE folder_name = VALUES(folder_name), category_id = VALUES(category_id);

INSERT INTO folder (folder_id, category_id, folder_name, parent_folder_id, created_at, updated_at)
VALUES (14, 14, '영상·뮤직', NULL, NOW(), NOW())
    ON DUPLICATE KEY UPDATE folder_name = VALUES(folder_name), category_id = VALUES(category_id);

INSERT INTO folder (folder_id, category_id, folder_name, parent_folder_id, created_at, updated_at)
VALUES (15, 15, '맛집·여행', NULL, NOW(), NOW())
    ON DUPLICATE KEY UPDATE folder_name = VALUES(folder_name), category_id = VALUES(category_id);

INSERT INTO folder (folder_id, category_id, folder_name, parent_folder_id, created_at, updated_at)
VALUES (16, 16, '기타', NULL, NOW(), NOW())
    ON DUPLICATE KEY UPDATE folder_name = VALUES(folder_name), category_id = VALUES(category_id);


INSERT INTO emotion (emotion_id, name) VALUES (1, '즐거움') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO emotion (emotion_id, name) VALUES (2, '평온') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO emotion (emotion_id, name) VALUES (3, '설렘') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO emotion (emotion_id, name) VALUES (4, '슬픔') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO emotion (emotion_id, name) VALUES (5, '짜증') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO emotion (emotion_id, name) VALUES (6, '분노') ON DUPLICATE KEY UPDATE name = VALUES(name);



INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (1, '네이버', 'naver.com', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);

INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (2, '네이버 블로그', 'blog.naver.com', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);

INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (3, '네이버 카페', 'cafe.naver.com', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);

INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (4, '네이버 지식인', 'kin.naver.com', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);

INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (5, '네이버 쇼핑', 'shopping.naver.com', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);

INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (6, '깃허브', 'github.com', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);

INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (7, '링크드인', 'linkedin.com', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);

INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (8, '티스토리', 'tistory.com', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);

INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (9, '구글', 'google.com', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);

INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (10, '뉴욕타임즈', 'nytimes.com', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);

INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (11, '브런치스토리', 'brunch.co.kr', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);

INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (12, '벨로그', 'velog.io', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);

INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (13, '다음', 'daum.net', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);

INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (14, '잡코리아', 'jobkorea.co.kr', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);

INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (15, '원티드', 'wanted.co.kr', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);

INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (16, '무신사', 'musinsa.com', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);

INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (17, '11번가', '11st.co.kr', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);

INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (18, '인스타그램', 'instagram.com', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);

INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (19, '트위터 (X)', 'twitter.com', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);

INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (20, '페이스북', 'facebook.com', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);

INSERT INTO domain (domain_id, name, domain_tail, image_url)
VALUES (21, '기타', 'invalid', NULL)
    ON DUPLICATE KEY UPDATE name = VALUES(name), domain_tail = VALUES(domain_tail), image_url = VALUES(image_url);
