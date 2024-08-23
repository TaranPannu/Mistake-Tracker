package com.example.mistaketracker.DaggerHilt

import android.content.Context
import com.example.mistaketracker.Retrofit.RetrofitServices
import com.example.mistaketracker.Room.Dao
import com.example.mistaketracker.Room.MistakeDatabase
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.Module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    @Singleton // single instance of retrofit will be throught the App..9
    @Provides
    fun provideRetrofit(): Retrofit
    {
        return Retrofit.Builder().baseUrl("**************").addConverterFactory(
            GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideRetrofitServices(retrofit: Retrofit): RetrofitServices
    {
        return retrofit.create(RetrofitServices::class.java)
    }
//    private const val BASE_URL = "http://10.0.0.126:8091"
//
//    private val client = OkHttpClient.Builder()
//        .addInterceptor { chain ->
//            val request = chain.request()
//            val jwtToken = "getJwtToken()" // Retrieve JWT token from storage
//            val newRequest = request.newBuilder()
//                .apply {
//                    jwtToken?.let {
//                        header("Authorization", "Bearer $it")
//                    }
//                }
//                .build()
//            chain.proceed(newRequest)
//        }
//        .build()


//    @Singleton
//    @Provides
//    fun providesRepo(retrofitServices: RetrofitServices) : Repo
//    {
//        return Repo(retrofitServices)
//    }

}
