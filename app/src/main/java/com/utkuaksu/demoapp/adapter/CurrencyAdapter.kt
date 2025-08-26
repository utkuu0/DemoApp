package com.utkuaksu.demoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utkuaksu.demoapp.R
import com.utkuaksu.demoapp.model.Currency

class CurrencyAdapter(
    private val currencyList: List<Currency>
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCurrency: TextView = itemView.findViewById(R.id.tvCurrency)
        val tvBuying: TextView = itemView.findViewById(R.id.tvBuying)
        val tvSelling: TextView = itemView.findViewById(R.id.tvSelling)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_currency, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencyList[position]
        holder.tvCurrency.text = currency.name
        holder.tvBuying.text = currency.buying.toString()
        holder.tvSelling.text = currency.selling.toString()
    }

    override fun getItemCount(): Int = currencyList.size
}
