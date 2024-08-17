package com.example.mistaketracker.DepInjec

import android.content.Context
import com.example.mistaketracker.Room.Dao
import com.example.mistaketracker.Room.MistakeDatabase
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.Module

@InstallIn(SingletonComponent::class)// module only excist till the application excist once the application finish, module will also be destroyed
@Module
class Module {
    @Provides
    @Singleton
    fun provideMistakeDatabase(@ApplicationContext context: Context): MistakeDatabase {
        return MistakeDatabase.invoke(context)
    }

    @Provides
    fun provideMistakeDao(database: MistakeDatabase): Dao {
        return database.getMistakeDao()
    }
}
