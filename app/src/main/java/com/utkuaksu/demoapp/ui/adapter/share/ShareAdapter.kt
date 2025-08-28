package com.utkuaksu.demoapp.ui.adapter.share

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utkuaksu.demoapp.R
import com.utkuaksu.demoapp.data.model.share.Share

class ShareAdapter(
    private var shareList: List<Share>
) : RecyclerView.Adapter<ShareAdapter.ShareViewHolder>() {

    fun updateList(newList: List<Share>) {
        shareList = newList
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
        val share = shareList[position]

        //Glide kullanarak ICON'u çektim
        Glide.with(holder.tvShareIcon.context)
            .load(share.icon)
            .into(holder.tvShareIcon)

        holder.tvShare.text = share.text
        holder.tvShareLastPrice.text = share.lastprice.toString()
        holder.tvShareRate.text = share.rate.toString()

        //Rate değerine göre color verildi.
        if (share.rate <= 0) {
            holder.tvShareRate.setTextColor(
                ContextCompat.getColor(holder.tvShareRate.context, R.color.red)
            )
        } else {
            holder.tvShareRate.setTextColor(
                ContextCompat.getColor(holder.tvShareRate.context, R.color.green)
            )
        }
    }

    override fun getItemCount(): Int = shareList.size
}