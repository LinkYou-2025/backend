package com.umc.linkyou.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc.linkyou.domain.QLinku;
import com.umc.linkyou.domain.QUsers;
import com.umc.linkyou.domain.mapping.QUsersLinku;
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
        QUsersLinku usersLinku = QUsersLinku.usersLinku;
        QLinku linku = QLinku.linku1;

        return queryFactory
                .select(linku.category.categoryId.countDistinct())
                .from(usersLinku)
                .join(linku).on(usersLinku.linku.linkuId.eq(linku.linkuId))
                // 또는 .join(usersLinku.linku, linku)
                .where(usersLinku.user.id.eq(userId))
                .fetchOne();
    }
    // 내가 만든 ai 링크 조회

}
