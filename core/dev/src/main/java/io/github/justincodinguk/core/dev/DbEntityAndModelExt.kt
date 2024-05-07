package io.github.justincodinguk.core.dev

import android.net.Uri
import io.github.justincodinguk.core.database.entity.Post as PostEntity
import io.github.justincodinguk.core.database.entity.User as UserEntity
import io.github.justincodinguk.core.model.Post
import io.github.justincodinguk.core.model.User

fun PostEntity.toModelPost() : Post {
    return Post(
        id = id,
        title = title,
        body = body,
        author = User(ownerId,ownerName,ownerProfile),
        likeCount = likeCount,
        photoUrls = photos.map { it.toString() }
    )
}

fun Post.toEntityPost(
    isFavorite: Boolean = false,
    isSelf: Boolean = false
) : PostEntity {
    return PostEntity(
        id = id,
        title = title,
        body = body,
        ownerId = author.id,
        ownerName = author.name,
        ownerProfile = author.profileImage,
        likeCount = likeCount,
        photos = photoUrls.map { Uri.parse(it) },
        isFavorite = isFavorite,
        isSelfCreated = isSelf
    )
}

fun UserEntity.toModelUser() : User {
    return User(
        id = id,
        name = name,
        profileImage = profileImageUrl
    )
}

fun User.toEntityUser() : UserEntity {
    return UserEntity(
        id = id,
        name = name,
        profileImageUrl = profileImage
    )
}