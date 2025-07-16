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



INSERT INTO domain (domain_id, name, domain_tail, image_url) VALUES
(1, 'invalid', 'invalid', NULL),
(2, 'blog.naver', 'blog.naver.com', NULL),
(3, 'cafe.naver', 'cafe.naver.com', NULL),
(4, 'kin.naver', 'kin.naver.com', NULL),
(5, 'shopping.naver', 'shopping.naver.com', NULL),
(6, 'github', 'github.com', NULL),
(7, 'linkedin', 'linkedin.com', NULL),
(8, 'tistory', 'tistory.com', NULL),
(9, 'google', 'google.com', NULL),
(10, 'nytimes', 'nytimes.com', NULL),
(11, 'brunch', 'brunch.co.kr', NULL),
(12, 'velog', 'velog.io', NULL),
(13, 'daum', 'daum.net', NULL),
(14, 'jobkorea', 'jobkorea.co.kr', NULL),
(15, 'wanted', 'wanted.co.kr', NULL),
(16, 'musinsa', 'musinsa.com', NULL),
(17, '11st', '11st.co.kr', NULL),
(18, 'instagram', 'instagram.com', NULL),
(19, 'twitter', 'twitter.com', NULL),
(20, 'facebook', 'facebook.com', NULL),
(21, 'naver', 'naver.com', NULL)
    ON DUPLICATE KEY UPDATE
                         name = VALUES(name),
                         domain_tail = VALUES(domain_tail),
                         image_url = VALUES(image_url);

INSERT INTO job (job_id, name) VALUES
                                   (1, '고등학생'),
                                   (2, '대학생'),
                                   (3, '직장인'),
                                   (4, '자영업자'),
                                   (5, '프리랜서'),
                                   (6, '취준생')
    ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO situation (situation_id, name) VALUES
                                               (1, '통학 중'),
                                               (2, '공부 중'),
                                               (3, '식사 중'),
                                               (4, '시험 준비'),
                                               (5, '친구랑'),
                                               (6, '쇼핑 중'),
                                               (7, '휴식 중'),
                                               (8, '자기 전'),
                                               (9, '과제 중'),
                                               (10, '알바 중'),
                                               (11, '트렌드 확인'),
                                               (12, '데이트 중'),
                                               (13, '출퇴근'),
                                               (14, '업무 중'),
                                               (15, '커리어 고민'),
                                               (16, '업무 준비 중'),
                                               (17, '작업 중'),
                                               (18, '운동 중'),
                                               (19, '자소서 작성'),
                                               (20, '면접 준비'),
                                               (21, '요리 중')
    ON DUPLICATE KEY UPDATE name = VALUES(name);

-- 고등학생(1)
INSERT INTO situation_job (situation_id, job_id) VALUES
                                                     (1, 1), (2, 1), (3, 1), (4, 1), (5, 1), (6, 1), (7, 1), (8, 1);

-- 대학생(2)
INSERT INTO situation_job (situation_id, job_id) VALUES
                                                     (9, 2), (1, 2), (6, 2), (10, 2), (11, 2), (12, 2), (7, 2), (8, 2);

-- 직장인(3)
INSERT INTO situation_job (situation_id, job_id) VALUES
                                                     (13, 3), (11, 3), (14, 3), (15, 3), (6, 3), (12, 3), (7, 3), (8, 3);

-- 자영업자(4)
INSERT INTO situation_job (situation_id, job_id) VALUES
                                                     (13, 4), (16, 4), (12, 4), (3, 4), (6, 4), (11, 4), (7, 4), (8, 4);

-- 프리랜서(5)
INSERT INTO situation_job (situation_id, job_id) VALUES
                                                     (17, 5), (6, 5), (11, 5), (12, 5), (18, 5), (3, 5), (7, 5), (8, 5);

-- 취준생(6)
INSERT INTO situation_job (situation_id, job_id) VALUES
                                                     (19, 6), (20, 6), (21, 6), (11, 6), (6, 6), (18, 6), (7, 6), (8, 6);



