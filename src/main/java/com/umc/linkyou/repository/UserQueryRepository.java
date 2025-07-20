package com.umc.linkyou.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc.linkyou.domain.QAiArticle;
import com.umc.linkyou.domain.QLinku;
import com.umc.linkyou.domain.QUsers;
import com.umc.linkyou.domain.mapping.QUsersLinku;
import com.umc.linkyou.domain.mapping.folder.QUsersFolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {

    private final JPAQueryFactory queryFactory;

    // 유저 닉네임 조회
    public String findNicknameByUserId(Long userId) {
        QUsers users = QUsers.users;

        return queryFactory
                .select(users.nickName)
                .from(users)
                .where(users.id.eq(userId))
                .fetchOne();

    }

    // 유저 링크 조회
    public Long countLinksByUserId(Long userId) {
        QUsersLinku usersLinku = QUsersLinku.usersLinku;

        return queryFactory
                .select(usersLinku.count())
                .from(usersLinku)
                .where(usersLinku.user.id.eq(userId))
                .fetchOne();
    }
    // 유저 폴더 조회
    public Long countFoldersByUserId(Long userId) {
        QUsersFolder usersFolder = QUsersFolder.usersFolder;

        return queryFactory
                .select(usersFolder.count())
                .from(usersFolder)
                .where(usersFolder.user.id.eq(userId))
                .fetchOne();
    }
    // 내가 만든 ai 링크 조회
    public Long countAiLinksByUserId(Long userId) {
        QUsersLinku usersLinku = QUsersLinku.usersLinku;
        QLinku linku = QLinku.linku1;
        QAiArticle aiArticle = QAiArticle.aiArticle;

        return queryFactory
                .select(aiArticle.count())
                .from(usersLinku)
                .join(usersLinku.linku, linku)
                .join(linku.aiArticle, aiArticle)
                .where(usersLinku.user.id.eq(userId))
                .fetchOne();
    }
}
