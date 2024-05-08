package io.github.justincodinguk.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.justincodinguk.core.database.HeritageHubDatabase
import io.github.justincodinguk.core.database.dao.FollowingUsersDao
import io.github.justincodinguk.core.database.dao.PostsDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideHeritageHubDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
            context,
            HeritageHubDatabase::class.java,
            "heritage_hub.db"
        ).build()

    @Provides
    @Singleton
    fun providePostsDao(
        database: HeritageHubDatabase
    ) : PostsDao {
        return database.getPostsDao()
    }

    @Provides
    @Singleton
    fun provideFollowingUsersDao(
        database: HeritageHubDatabase
    ) : FollowingUsersDao {
        return database.getFollowingUsersDao()
    }

}
