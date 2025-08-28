package com.utkuaksu.demoapp.ui.adapter.emtia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utkuaksu.demoapp.R
import com.utkuaksu.demoapp.data.model.emtia.Emtia

class EmtiaAdapter(
    private var emtiaList: List<Emtia>
): RecyclerView.Adapter<EmtiaAdapter.EmtiaViewHolder>() {

    fun updateList(newList: List<Emtia>) {
        emtiaList = newList
        notifyDataSetChanged()
    }

    class EmtiaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEmtia: TextView = itemView.findViewById(R.id.tvEmtia)
        val tvEmtiaBuying: TextView = itemView.findViewById(R.id.tvEmtiBuying)
        val tvEmtiaSelling: TextView = itemView.findViewById(R.id.tvEmtiaSelling)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmtiaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_emtia, parent, false)

        return EmtiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmtiaViewHolder, position: Int) {
        val emtia = emtiaList[position]
        holder.tvEmtia.text = emtia.name
        holder.tvEmtiaBuying.text = emtia.buying.toString()
        holder.tvEmtiaSelling.text = emtia.selling.toString()
    }

    override fun getItemCount(): Int = emtiaList.size
}