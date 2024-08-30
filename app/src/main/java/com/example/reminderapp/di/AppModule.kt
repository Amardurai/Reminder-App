package com.example.reminderapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.reminderapp.data.local.ReminderDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideRoomDataBase(@ApplicationContext context: Context): ReminderDataBase {
        return Room.databaseBuilder(context, ReminderDataBase::class.java, "reminder_database")
            .build()
    }

    @Provides
    @Singleton
    fun provideReminderDao(reminderDataBase: ReminderDataBase) = reminderDataBase.reminderDao

}