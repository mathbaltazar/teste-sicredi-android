package br.com.testedevandroid.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.testedevandroid.R
import br.com.testedevandroid.data.model.Event
import br.com.testedevandroid.utils.DefaultRequestOptions
import br.com.testedevandroid.utils.EventClickListener
import br.com.testedevandroid.utils.Formatter
import com.bumptech.glide.Glide

class EventsAdapter : ListAdapter<Event, RecyclerView.ViewHolder>(COMPARATOR) {
    private var eventListener: EventClickListener? = null

    private object COMPARATOR : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean =
            oldItem == newItem
    }

    fun setEventClickListener(listener: EventClickListener) {
        this.eventListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_event_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.imageview_event_item)
        val textviewTitle = holder.itemView.findViewById<TextView>(R.id.textview_title_event_item)
        val textviewDescription =
            holder.itemView.findViewById<TextView>(R.id.textview_description_event_item)
        val textviewPrice = holder.itemView.findViewById<TextView>(R.id.textview_price_event_item)
        val textviewDate = holder.itemView.findViewById<TextView>(R.id.textview_date_event_item)
        val evento = getItem(position)

        Glide.with(holder.itemView)
            .applyDefaultRequestOptions(DefaultRequestOptions.instance())
            .load(evento.image)
            .into(imageView)

        textviewTitle.text = evento.title
        textviewDescription.text = evento.description
        textviewPrice.text = Formatter.formatCurrency(evento.price)
        textviewDate.text = Formatter.formatDate(evento.date)

        holder.itemView.setOnClickListener { eventListener?.onEventClick(evento.id) }
    }

    private class EventViewHolder(view: View) : RecyclerView.ViewHolder(view)

}
