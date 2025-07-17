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


-- 즐거움 (emotion_id = 1)
INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (1, '(닉네임)님, 요즘 기분 좋은 일이 많았죠? 그 흐름을 더 이어갈 콘텐츠들이에요.', '지금의 긍정적인 에너지를 놓치지 말고, 조금 더 넓혀봐요!')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (1, '(닉네임)님, 기분 좋을 때 찾는 콘텐츠는 기억에 오래 남아요. 오늘도 그런 하루가 되기를!', '마음이 열려 있을 때, 생각보다 더 많은 걸 받아들일 수 있거든요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (1, '(닉네임)님의 좋은 하루, 더 좋은 생각으로 채워볼까요? 지금 이 기분을 조금 더 확장해줄 콘텐츠들이에요.', '행복은 종종 아주 사소한 클릭 하나에서 시작되니까요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (1, '(닉네임)님, 기분 좋은 지금, 뭔가 새롭고 흥미로운 걸 만나보는 건 어때요?', '즐거움은 나누면 배가 되죠. 이 감정을 오래 기억할 수 있기를!')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

-- 평온 (emotion_id = 2)
INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (2, '(닉네임)님, 딱히 무슨 감정이 있는 건 아니지만, 그럴수록 이런 콘텐츠가 필요한 순간일지도 몰라요.', '의미 없는 것들 속에서 가끔 중요한 게 튀어나오기도 해요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (2, '(닉네임)님, 그냥 그런 하루였죠. 아무 생각 없이 클릭해도 괜찮아요.', '가끔은 아무 의도 없이 마주한 것들이, 의외로 오래 남아요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (2, '(닉네임)님, 감정의 여백이 많은 하루, 그 틈을 살짝 채워줄 콘텐츠예요.', '오늘은 아무 기대 없이 읽어도 좋아요. 그런 날도 필요한 법이니까요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (2, '(닉네임)님, 할 일은 많은데 마음은 비어있을 때, 그냥 스쳐볼 수 있는 콘텐츠가 필요하잖아요.', '모두가 특별할 필요는 없어요. 그냥 그런 오늘도 충분히 의미 있어요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

-- 설렘 (emotion_id = 3)
INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (3, '(닉네임)님, 설레는 기분이 있다면, 뭔가가 시작될 준비가 된 거예요.', '지금의 감정이 어디로 가게 될지, 조금 더 지켜봐도 좋겠어요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (3, '(닉네임)님의 마음이 조금씩 움직이는 것 같아요. 그 떨림에 어울리는 콘텐츠를 골라봤어요.', '설렘은 가장 강력한 동기부여예요. 지금, 그 에너지를 믿어보세요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (3, '(닉네임)님, 기대하는 일이 있을 때, 마음이 조금 더 섬세해지죠.', '설렘을 오래 붙잡지 않아도 돼요. 잠시 머물러도 충분하니까요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (3, '(닉네임)님, 어떤 일이든 잘 풀릴 것 같은 예감이 드는 날엔 이런 콘텐츠가 의외로 잘 맞아요.', '좋은 에너지는 늘 다음을 향하고 있어요. 지금도 그중 하나일 거예요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

-- 슬픔 (emotion_id = 4)
INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (4, '(닉네임)님, 요즘 마음이 자주 무거웠죠. 한 걸음 멈춰, 지금의 나에게 필요한 것들을 함께 살펴봐요.', '계속 괜찮은 척 안 해도 돼요. 마음이 머무를 곳, 여기 있어요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (4, '(닉네임)님, 혼자만 복잡한 게 아니에요. 비슷한 고민을 지나온 이들의 이야기로 당신의 오늘을 밝혀줄게요.', '지금 이 순간, 아무것도 하지 않아도 괜찮아요. 우린 늘 다시 일어설 힘을 가지고 있으니까요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (4, '생각은 많은데 정리가 안 되죠. (닉네임)님의 머릿속을 환기시켜줄 콘텐츠들을 모았어요', '지금 떠오르지 않아도 괜찮아요. 영감은 가끔, 쉬고 있을 때 더 잘 찾아오거든요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (4, '(닉네임)님, 마음이 무거운 날엔, 다독임보다 방향이 필요한 걸지도 몰라요.', '지금은 가만히 있어도 돼요. 그 자체로도 충분히 잘하고 있는 거니까요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

-- 짜증 (emotion_id = 5)
INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (5, '(닉네임)님, 요즘 작은 일에도 쉽게 예민해지죠. 그럴 땐 잠깐 다른 결의 콘텐츠를 보는 것도 괜찮아요.', '짜증을 억누르지 않아도 돼요. 단지, 그 에너지를 흘릴 통로만 있으면 돼요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (5, '(닉네임)님의 답답한 기분, 괜찮아요. 이 콘텐츠들이 잠시 머리를 환기시켜줄 수 있을 거예요.”', '감정은 느끼는 대로 두세요. 똑똑한 선택은 가끔 쉬는 것부터 시작돼요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (5, '(닉네임)님, 짜증이 쌓인 날엔 의미 없는 정보도 거슬리죠. 그래서 가볍고 유연한 콘텐츠들로 준비했어요.', '감정이 무뎌지기 전, 살짝 숨 고르듯 흘려보면 좋아요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (5, '(닉네임)님, 계속해서 끓어오르기 전에, 지금 멈춰서 다른 쪽을 바라보는 것도 하나의 전략이에요.', '감정의 방향을 바꾸기보다, 감정이 흐를 수 있게 해주세요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

-- 분노 (emotion_id = 6)
INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (6, '(닉네임)님, 화가 날 땐 말보다 생각이 많아지죠. 그 감정을 다루기 위한 콘텐츠들을 모았어요.', '감정은 정리하지 않아도 괜찮아요. 다만, 흘릴 구멍은 있어야 하니까요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (6, '(닉네임)님, 억울하고 화가 날 땐, 세상이 너무 불공평하게 느껴지죠. 그럴 때 필요한 건 날카로운 인사이트일지도 몰라요.', '이유 없는 감정은 없어요. 지금 이 감정도 당신의 한 부분이에요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (6, '(닉네임)님, 분노는 무기력이 아니라 에너지예요. 어떻게 쓸지, 지금 여기서 천천히 살펴볼 수 있어요.', '당장 해결은 안 되더라도, 그 감정을 외면하지 않아줘서 고마워요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);

INSERT INTO curation_ment (emotion_id, header_text, footer_text) VALUES
    (6, '(닉네임)님의 감정이 폭발하기 전에 한 번 숨 고를 수 있도록 도와줄 콘텐츠들이에요.', '모든 감정은 지나가요. 오늘의 분노도 결국 당신을 더 단단하게 만들 거예요.')
    ON DUPLICATE KEY UPDATE header_text = VALUES(header_text), footer_text = VALUES(footer_text);



INSERT INTO job (job_id, name) VALUES
    (1, '고등학생'),
    (2, '대학생'),
    (3, '직장인'),
    (4, '자영업자'),
    (5, '프리랜서'),
    (6, '취준생')
    ON DUPLICATE KEY UPDATE name = VALUES(name);


INSERT INTO situation (situation_id, name) VALUES
-- 고등학생 (1~8)
(1, '통학 중'),
(2, '공부 중'),
(3, '식사 중'),
(4, '시험 준비'),
(5, '친구랑'),
(6, '쇼핑 중'),
(7, '휴식 중'),
(8, '자기 전'),

-- 대학생 (9~16)
(9, '과제 중'),
(10, '통학 중'),
(11, '쇼핑 중'),
(12, '알바 중'),
(13, '트렌드 확인'),
(14, '데이트 중'),
(15, '휴식 중'),
(16, '자기 전'),

-- 직장인 (17~24)
(17, '출퇴근'),
(18, '트렌드 확인'),
(19, '업무 중'),
(20, '커리어 고민'),
(21, '쇼핑 중'),
(22, '데이트 중'),
(23, '휴식 중'),
(24, '자기 전'),

-- 자영업자 (25~32)
(25, '출퇴근'),
(26, '업무 준비 중'),
(27, '데이트 중'),
(28, '식사'),
(29, '쇼핑 중'),
(30, '트렌드 확인'),
(31, '휴식 중'),
(32, '자기 전'),

-- 프리랜서 (33~40)
(33, '작업 중'),
(34, '쇼핑 중'),
(35, '트렌드 확인'),
(36, '데이트 중'),
(37, '운동 중'),
(38, '식사'),
(39, '휴식 중'),
(40, '자기 전'),

-- 취준생 (41~48)
(41, '자소서 작성'),
(42, '면접 준비'),
(43, '요리 중'),
(44, '트렌드 확인'),
(45, '쇼핑 중'),
(46, '운동 중'),
(47, '휴식 중'),
(48, '자기 전')
    ON DUPLICATE KEY UPDATE name = VALUES(name);


-- 고등학생 (job_id = 1)
INSERT INTO situation_job (situation_id, job_id) VALUES
(1, 1), (2, 1), (3, 1), (4, 1), (5, 1), (6, 1), (7, 1), (8, 1),

-- 대학생 (job_id = 2)
(9, 2), (10, 2), (11, 2), (12, 2), (13, 2), (14, 2), (15, 2), (16, 2),

-- 직장인 (job_id = 3)
(17, 3), (18, 3), (19, 3), (20, 3), (21, 3), (22, 3), (23, 3), (24, 3),

-- 자영업자 (job_id = 4)
(25, 4), (26, 4), (27, 4), (28, 4), (29, 4), (30, 4), (31, 4), (32, 4),

-- 프리랜서 (job_id = 5)
(33, 5), (34, 5), (35, 5), (36, 5), (37, 5), (38, 5), (39, 5), (40, 5),

-- 취준생 (job_id = 6)
(41, 6), (42, 6), (43, 6), (44, 6), (45, 6), (46, 6), (47, 6), (48, 6);
