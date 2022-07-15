package com.kimdo.myrunningapp.di

import android.content.Context
import androidx.room.Room
import com.kimdo.myrunningapp.db.RunDao
import com.kimdo.myrunningapp.db.RunDatabase
import com.kimdo.myrunningapp.other.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RunDatabase {
        return Room.databaseBuilder(context, RunDatabase::class.java, DATABASE_NAME).build()
    }

    @Singleton
    @Provides
    fun provideRunDao(db: RunDatabase): RunDao{
        return db.getDao()
    }
}