package br.com.testedevandroid.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.testedevandroid.R
import br.com.testedevandroid.ui.adapters.PeopleAdapter
import br.com.testedevandroid.utils.DefaultRequestOptions
import br.com.testedevandroid.utils.Formatter
import br.com.testedevandroid.viewmodels.EventsViewModel
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class EventDetailsFragment : Fragment() {

    private val viewModel: EventsViewModel by activityViewModels()

    private lateinit var imageView: ImageView
    private lateinit var textViewTitle: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var textViewDate: TextView
    private lateinit var textViewPrice: TextView
    private lateinit var mapView: MapView
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonCheckin: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.imageview_event_detail)
        textViewTitle = view.findViewById(R.id.textview_title_event_detail)
        textViewDescription = view.findViewById(R.id.textview_description_event_detail)
        textViewDate = view.findViewById(R.id.textview_date_event_detail)
        textViewPrice = view.findViewById(R.id.textview_price_event_detail)
        mapView = view.findViewById(R.id.mapview_event_detail)
        recyclerView = view.findViewById(R.id.recyclerview_people_event_detail)
        buttonCheckin = view.findViewById(R.id.button_checkin_event)


        mapView.onCreate(savedInstanceState)

        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.eventoDetalhado.observe(viewLifecycleOwner) { eventoDetalhado ->
            Glide.with(requireContext())
                .applyDefaultRequestOptions(DefaultRequestOptions.instance())
                .load(eventoDetalhado.image)
                .into(imageView)

            textViewTitle.text = eventoDetalhado.title
            textViewDescription.text = eventoDetalhado.description
            textViewDate.text = Formatter.formatDate(eventoDetalhado.date)
            textViewPrice.text = Formatter.formatCurrency(eventoDetalhado.price)

            mapView.getMapAsync { googleMap ->
                val localizacao =
                    LatLng(
                        eventoDetalhado.latitude.toDouble(),
                        eventoDetalhado.longitude.toDouble()
                    )
                googleMap.addMarker(MarkerOptions().position(localizacao).draggable(false))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacao, 15.0f))
            }

            val adapter = PeopleAdapter()
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            adapter.submitList(eventoDetalhado.people)

            buttonCheckin.setOnClickListener {
                findNavController().navigate(R.id.action_eventDetailsFragment_to_checkInFragment)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

}