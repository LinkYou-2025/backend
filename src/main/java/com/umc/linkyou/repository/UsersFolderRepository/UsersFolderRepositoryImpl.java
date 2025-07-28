package com.umc.linkyou.repository.UsersFolderRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc.linkyou.domain.folder.Folder;
import com.umc.linkyou.domain.mapping.folder.QUsersFolder;
import com.umc.linkyou.domain.mapping.folder.UsersFolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsersFolderRepositoryImpl implements UsersFolderRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Folder> searchFolders(
            Long userId,
            Long categoryId,
            Long parentFolderId,
            String folderName,
            Boolean isBookmarked,
            Boolean isShared
    ) {
        QUsersFolder usersFolder = QUsersFolder.usersFolder;
        BooleanBuilder builder = new BooleanBuilder();

        // 사용자 ID
        if (userId != null) {
            builder.and(usersFolder.user.id.eq(userId));
        }

        if (categoryId != null) {
            builder.and(usersFolder.folder.category.categoryId.eq(categoryId));
        }

        // 중분류 폴더는 부모 폴더 null
        if (parentFolderId != null) {
            builder.and(usersFolder.folder.parentFolder.folderId.eq(parentFolderId));
        }

        if (folderName != null && !folderName.isEmpty()) {
            builder.and(usersFolder.folder.folderName.containsIgnoreCase(folderName));
        }

        // 북마크
        if (isBookmarked != null) {
            builder.and(usersFolder.isBookmarked.eq(isBookmarked));
        }

        // 공유 받은 폴더인지 (본인이 주인이 아닌 폴더인지)
        if (isShared != null) {
            if (isShared) {
                builder.and(usersFolder.isOwner.eq(false));
            } else {
                builder.and(usersFolder.isOwner.eq(true));
            }
        }

        return queryFactory
                .select(usersFolder.folder)
                .from(usersFolder)
                .where(builder)
                .fetch();
    }

    @Override
    public Optional<UsersFolder> findByUserIdAndFolderId(Long userId, Long folderId) {
        QUsersFolder usersFolder = QUsersFolder.usersFolder;
        BooleanBuilder builder = new BooleanBuilder();

        // 사용자 ID
        if (userId != null) {
            builder.and(usersFolder.user.id.eq(userId));
        }

        if (folderId != null) {
            builder.and(usersFolder.folder.folderId.eq(folderId));
        }

        UsersFolder result = queryFactory
                .selectFrom(usersFolder)
                .where(builder)
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
