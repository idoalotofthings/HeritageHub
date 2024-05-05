package io.github.justincodinguk.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.justincodinguk.core.data.repository.AuthRepository
import io.github.justincodinguk.core.data.repository.AuthRepositoryImpl
import io.github.justincodinguk.core.data.repository.PostsRepository
import io.github.justincodinguk.core.data.repository.PostsRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindPostRepositoryImpl(postsRepositoryImpl: PostsRepositoryImpl): PostsRepository

    @Binds
    fun bindAuthRepositoryImpl(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

}