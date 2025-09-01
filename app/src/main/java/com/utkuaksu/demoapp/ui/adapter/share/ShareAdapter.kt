package com.utkuaksu.demoapp.ui.adapter.share

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utkuaksu.demoapp.R
import com.utkuaksu.demoapp.data.model.share.Share

class ShareAdapter(
    private var shareList: List<Share>
) : RecyclerView.Adapter<ShareAdapter.ShareViewHolder>(), Filterable{

    private var filteredList: List<Share> = shareList

    fun updateList(newList: List<Share>) {
        shareList = newList
        filteredList = newList
        notifyDataSetChanged()
    }

    class ShareViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvShareIcon: ImageView = itemView.findViewById(R.id.tvShareIcon)
        val tvShare: TextView = itemView.findViewById(R.id.tvShare)
        val tvShareLastPrice: TextView = itemView.findViewById(R.id.tvShareLastPrice)
        val tvShareRate: TextView = itemView.findViewById(R.id.tvShareRate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShareViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_share, parent, false)
        return ShareViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShareViewHolder, position: Int) {
        val share = filteredList[position]

        //Glide kullanarak ICON'u çektim
        Glide.with(holder.tvShareIcon.context)
            .load(share.icon)
            .into(holder.tvShareIcon)

        holder.tvShare.text = share.text
        holder.tvShareLastPrice.text = share.lastprice.toString()
        holder.tvShareRate.text = share.rate.toString()

        //Rate değerine göre color verdim.
        if (share.rate <= 0) {
            holder.tvShareRate.setTextColor(
                ContextCompat.getColor(holder.tvShareRate.context, R.color.currency_down)
            )
        } else {
            holder.tvShareRate.setTextColor(
                ContextCompat.getColor(holder.tvShareRate.context, R.color.currency_up)
            )
        }
    }

    override fun getItemCount(): Int = filteredList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase()?.trim() ?: ""
                val result = if (query.isEmpty()) {
                    shareList
                } else {
                    shareList.filter {
                        it.text.lowercase().contains(query) ||
                                it.code.lowercase().contains(query)
                    }
                }
                return FilterResults().apply { values = result }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as? List<Share> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }
}