package br.com.testedevandroid.data.repository

import br.com.testedevandroid.data.model.CheckIn
import br.com.testedevandroid.data.model.Event
import br.com.testedevandroid.services.APIService
import br.com.testedevandroid.services.ServiceUtils

class EventRepository {
    private val service: APIService = ServiceUtils.createApi(APIService::class.java)

    suspend fun getEventos(): List<Event> {
        val listaEventos = service.getEvents()
        return listaEventos
    }

    suspend fun getEventoPelaId(id: Int): Event? {
        val response = service.getEventById(id)
        return if (response.isSuccessful) response.body()!!
        else null
    }

    suspend fun fazerCheckin(eventId: Int, nome: String, email: String): Boolean {
        val dto = CheckIn(eventId, nome, email)
        val response = service.fazerCheckin(dto)
        return response.isSuccessful
    }

}
