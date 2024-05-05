package io.github.justincodinguk.core.firebase.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.justincodinguk.core.firebase.auth.FirebaseAuthServiceImpl
import io.github.justincodinguk.core.firebase.firestore_service.UsersFirestoreService
import io.github.justincodinguk.core.firebase.storage.FirebaseStorageService

@Module
@InstallIn(SingletonComponent::class)
internal object FirebaseProvisionServiceModule {

    @Provides
    fun provideFirebaseStorageService(): FirebaseStorageService {
        return FirebaseStorageService()
    }

    @Provides
    fun provideFirebaseAuthServiceImpl(
        usersFirestoreService: UsersFirestoreService
    ): FirebaseAuthServiceImpl {
        return FirebaseAuthServiceImpl(usersFirestoreService)
    }

}