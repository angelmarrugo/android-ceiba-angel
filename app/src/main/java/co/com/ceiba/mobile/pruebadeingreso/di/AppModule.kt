package co.com.ceiba.mobile.pruebadeingreso.di

import android.content.Context
import androidx.room.Room
import co.com.ceiba.mobile.pruebadeingreso.db.PostDao
import co.com.ceiba.mobile.pruebadeingreso.db.UserDao
import co.com.ceiba.mobile.pruebadeingreso.db.UserRoomDatabase
import co.com.ceiba.mobile.pruebadeingreso.rest.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val authInterceptor = Interceptor { chain ->
            val url = chain.request().url.newBuilder()
                .build()

            val newRequest = chain.request()
                .newBuilder()
                .url(url)
                .build()

            chain.proceed(newRequest)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor).build()

        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiClient(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserDao(database: UserRoomDatabase): UserDao {
        return database.userDao()
    }

    @Singleton
    @Provides
    fun providePostDao(database: UserRoomDatabase): PostDao {
        return database.postDao()
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext app: Context): UserRoomDatabase {
        return Room.databaseBuilder(
            app,
            UserRoomDatabase::class.java,
            "json_database")
            .fallbackToDestructiveMigration()
            .build()
    }
}