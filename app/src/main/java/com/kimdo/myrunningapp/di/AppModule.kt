package com.kimdo.myrunningapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.kimdo.myrunningapp.db.RunDao
import com.kimdo.myrunningapp.db.RunDatabase
import com.kimdo.myrunningapp.other.Constants.DATABASE_NAME
import com.kimdo.myrunningapp.other.Constants.KEY_FIRST_TIME_TOGGLE
import com.kimdo.myrunningapp.other.Constants.KEY_NAME
import com.kimdo.myrunningapp.other.Constants.KEY_WEIGHT
import com.kimdo.myrunningapp.other.Constants.SHARED_PREFERENCES_NAME
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

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app: Context) =
        app.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideName(sharedPref: SharedPreferences) = sharedPref.getString(KEY_NAME, "") ?: ""

    @Singleton
    @Provides
    fun provideWeight(sharedPref: SharedPreferences) = sharedPref.getFloat(KEY_WEIGHT, 80f)

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPref: SharedPreferences) =
        sharedPref.getBoolean(KEY_FIRST_TIME_TOGGLE, true)
}