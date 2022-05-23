package br.com.testedevandroid.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.testedevandroid.R
import br.com.testedevandroid.ui.adapters.EventsAdapter
import br.com.testedevandroid.utils.EventClickListener
import br.com.testedevandroid.viewmodels.EventsViewModel
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EventsFragment : Fragment(), EventClickListener {

    private val viewModel: EventsViewModel by activityViewModels()


    private lateinit var progressIndicator: LinearProgressIndicator
    private lateinit var recyclerViewEvents: RecyclerView

    private val adapter = EventsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        progressIndicator = view.findViewById(R.id.events_progress_indicator)
        recyclerViewEvents = view.findViewById(R.id.recyclerview_events)

        recyclerViewEvents.adapter = adapter
        recyclerViewEvents.layoutManager = LinearLayoutManager(requireContext())
        adapter.setEventClickListener(this)

        subscribeObservers()

        lifecycleScope.launch {
            progressIndicator.visibility = View.VISIBLE
            delay(2000) // Apenas pra visualizar o progress
            viewModel.loadEventos()
        }
    }

    private fun subscribeObservers() {
        viewModel.eventos.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            progressIndicator.visibility = View.GONE

            if (it.isEmpty()) {
                Toast.makeText(requireContext(), "Falha ao carregar eventos", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onEventClick(eventId: Int) {

        lifecycleScope.launch {
            progressIndicator.visibility = View.VISIBLE

            if (viewModel.loadEvento(eventId)) {
                findNavController().navigate(R.id.action_eventsFragment_to_eventDetailsFragment)
            } else {
                Toast.makeText(requireContext(), "Falha ao recuperar evento", Toast.LENGTH_SHORT)
                    .show()
            }

            progressIndicator.visibility = View.GONE
        }
    }
}