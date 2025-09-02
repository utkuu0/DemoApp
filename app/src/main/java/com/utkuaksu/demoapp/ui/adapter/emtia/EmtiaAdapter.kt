package com.utkuaksu.demoapp.ui.adapter.emtia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utkuaksu.demoapp.R
import com.utkuaksu.demoapp.data.model.emtia.Emtia
import com.utkuaksu.demoapp.data.model.share.Share
import kotlin.text.lowercase

class EmtiaAdapter(
    private var emtiaList: List<Emtia>
): RecyclerView.Adapter<EmtiaAdapter.EmtiaViewHolder>(), Filterable {

    private var filteredList: List<Emtia> = emtiaList

    fun updateList(newList: List<Emtia>) {
        emtiaList = newList
        filteredList = newList
        notifyDataSetChanged()
    }

    class EmtiaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEmtia: TextView = itemView.findViewById(R.id.tvEmtia)
        val tvEmtiaBuying: TextView = itemView.findViewById(R.id.tvEmtiaBuying)
        val tvEmtiaSelling: TextView = itemView.findViewById(R.id.tvEmtiaSelling)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmtiaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_emtia, parent, false)
        return EmtiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmtiaViewHolder, position: Int) {
        val emtia = filteredList[position]
        holder.tvEmtia.text = emtia.name
        holder.tvEmtiaBuying.text = emtia.buying.toString()
        holder.tvEmtiaSelling.text = emtia.selling.toString()
    }

    override fun getItemCount(): Int = filteredList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase()?.trim() ?: ""
                val result = if (query.isEmpty()) {
                    emtiaList
                } else {
                    emtiaList.filter {
                        (it.test ?: "").lowercase().contains(query) ||
                                (it.name ?: "").lowercase().contains(query)
                    }
                }
                return FilterResults().apply { values = result }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as? List<Emtia> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }
}