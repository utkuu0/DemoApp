package com.utkuaksu.demoapp.ui.adapter.currency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utkuaksu.demoapp.R
import com.utkuaksu.demoapp.data.model.currency.Currency

class CurrencyAdapter(
    private var currencyList: List<Currency>
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    fun updateList(newList: List<Currency> ) {
        currencyList = newList
        notifyDataSetChanged()
    }

    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCurrency: TextView = itemView.findViewById(R.id.tvShare)
        val tvCurrencyBuying: TextView = itemView.findViewById(R.id.tvShareLastPrice)
        val tvCurrencySelling: TextView = itemView.findViewById(R.id.tvShareRate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_currency, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencyList[position]
        holder.tvCurrency.text = currency.name
        holder.tvCurrencyBuying.text = currency.buying.toString()
        holder.tvCurrencySelling.text = currency.selling.toString()
    }

    override fun getItemCount(): Int = currencyList.size
}