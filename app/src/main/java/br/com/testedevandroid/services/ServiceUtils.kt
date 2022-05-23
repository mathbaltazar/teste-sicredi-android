package br.com.testedevandroid.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceUtils {

    companion object {
        fun <T> createApi(service: Class<T>): T {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://5f5a8f24d44d640016169133.mockapi.io/api/")
                .build().create(service)
        }
    }

}
