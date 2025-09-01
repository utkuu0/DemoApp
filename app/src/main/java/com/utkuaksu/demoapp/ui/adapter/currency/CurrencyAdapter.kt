package com.utkuaksu.demoapp.ui.adapter.currency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utkuaksu.demoapp.R
import com.utkuaksu.demoapp.data.model.currency.Currency

class CurrencyAdapter(
    private var currencyList: List<Currency>
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>(), Filterable {

    private var filteredList: List<Currency> = currencyList

    fun updateList(newList: List<Currency>) {
        currencyList = newList
        filteredList = newList // filtrelenmiş listeyi de güncelle
        notifyDataSetChanged()
    }

    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCurrency: TextView = itemView.findViewById(R.id.tvCurrency)
        val tvCurrencyBuying: TextView = itemView.findViewById(R.id.tvCurrencyBuying)
        val tvCurrencySelling: TextView = itemView.findViewById(R.id.tvCurrencySelling)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_currency, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = filteredList[position]
        holder.tvCurrency.text = currency.name
        holder.tvCurrencyBuying.text = currency.buying.toString()
        holder.tvCurrencySelling.text = currency.selling.toString()
    }

    override fun getItemCount(): Int = filteredList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase()?.trim() ?: ""
                val result = if (query.isEmpty()) {
                    currencyList
                } else {
                    currencyList.filter {
                        it.name.lowercase().contains(query) ||
                                it.code.lowercase().contains(query)
                    }
                }
                return FilterResults().apply { values = result }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as? List<Currency> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }
}
