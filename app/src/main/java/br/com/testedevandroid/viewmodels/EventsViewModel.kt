package br.com.testedevandroid.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.testedevandroid.data.model.Event
import br.com.testedevandroid.data.repository.EventRepository

class EventsViewModel : ViewModel() {

    private val _eventoDetalhado = MutableLiveData<Event>()
    val eventoDetalhado: LiveData<Event> get() = _eventoDetalhado

    private val repository = EventRepository()

    private val _eventos = MutableLiveData<List<Event>>()
    val eventos: LiveData<List<Event>> get() = _eventos


    suspend fun loadEventos() {
        val eventos = repository.getEventos()
        _eventos.postValue(eventos)
    }

    suspend fun loadEvento(id: Int): Boolean {
        val evento = repository.getEventoPelaId(id)
        if (evento != null) {
            _eventoDetalhado.postValue(evento!!)
        }
        return evento != null
    }

    suspend fun fazerCheckInEmEvento(eventId: Int, nome: String, email: String): Boolean {
        return repository.fazerCheckin(eventId, nome, email)
    }

}