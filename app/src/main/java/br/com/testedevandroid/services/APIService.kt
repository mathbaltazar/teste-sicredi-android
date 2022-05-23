package br.com.testedevandroid.services

import br.com.testedevandroid.data.model.CheckIn
import br.com.testedevandroid.data.model.Event
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {

    @GET("events")
    suspend fun getEvents(): List<Event>

    @GET("events/{id}")
    suspend fun getEventById(@Path("id") id: Int): Response<Event>

    @POST("checkin")
    suspend fun fazerCheckin(@Body checkin: CheckIn): Response<Void>
}
