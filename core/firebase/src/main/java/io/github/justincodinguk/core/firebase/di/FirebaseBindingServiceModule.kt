package io.github.justincodinguk.core.firebase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.justincodinguk.core.firebase.auth.FirebaseAuthService
import io.github.justincodinguk.core.firebase.auth.FirebaseAuthServiceImpl
import io.github.justincodinguk.core.firebase.firestore_service.FirestoreService
import io.github.justincodinguk.core.firebase.firestore_service.PostsFirestoreService
import io.github.justincodinguk.core.firebase.firestore_service.UsersFirestoreService
import io.github.justincodinguk.core.model.Post
import io.github.justincodinguk.core.model.User


@Module
@InstallIn(SingletonComponent::class)
internal interface FirebaseBindingServiceModule {

    @Binds
    @PostService
    fun bindPostsFirestoreService(
        postsFirestoreService: PostsFirestoreService
    ): FirestoreService<Post>

    @Binds
    @UserService
    fun bindUsersFirestoreService(
        usersFirestoreService: UsersFirestoreService
    ): FirestoreService<User>


    @Binds
    fun bindFirebaseAuthService(
        firebaseAuthServiceImpl: FirebaseAuthServiceImpl
    ): FirebaseAuthService

}