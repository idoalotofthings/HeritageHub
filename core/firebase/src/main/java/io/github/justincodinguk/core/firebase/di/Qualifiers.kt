package io.github.justincodinguk.core.firebase.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UserService

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PostService