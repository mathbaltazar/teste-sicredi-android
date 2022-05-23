package br.com.testedevandroid.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.testedevandroid.data.model.Person

class PeopleAdapter : ListAdapter<Person, RecyclerView.ViewHolder>(COMPARATOR) {

    private object COMPARATOR : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.name == newItem.name && oldItem.email == newItem.email
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layout = android.R.layout.simple_expandable_list_item_2
        return PersonViewHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val person = getItem(position)

        holder.itemView.findViewById<TextView>(android.R.id.text1).text = person.name
        holder.itemView.findViewById<TextView>(android.R.id.text2).text = person.email
    }

    class PersonViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
