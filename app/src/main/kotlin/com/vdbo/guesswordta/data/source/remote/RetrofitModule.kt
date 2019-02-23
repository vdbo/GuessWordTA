package com.vdbo.guesswordta.data.source.remote

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module class RetrofitModule {

    @Provides @Singleton fun provideRetrofit() = Retrofit.Builder()
        .baseUrl("https://gist.githubusercontent.com/DroidCoder/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Provides @Singleton fun provideBApi(retrofit: Retrofit) = retrofit.create(BApi::class.java)

}