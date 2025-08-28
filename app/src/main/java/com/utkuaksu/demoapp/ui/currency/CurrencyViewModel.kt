package com.utkuaksu.demoapp.ui.currency

import androidx.lifecycle.*
import com.utkuaksu.demoapp.data.model.currency.Currency
import com.utkuaksu.demoapp.data.repository.currency.CurrencyRepository
import com.utkuaksu.demoapp.utils.Resource
import kotlinx.coroutines.launch

class CurrencyViewModel( private val repository: CurrencyRepository) : ViewModel() {

    private val _currencies = MutableLiveData<Resource<List<Currency>>>()
    init {

    }
    val currencies: LiveData<Resource<List<Currency>>> = _currencies

    fun fetchCurrencies() {
        viewModelScope.launch {
            _currencies.postValue(Resource.Loading())
            try {
                val response = repository.getCurrencies()
                if (response.isSuccessful && response.body() != null) {
                    _currencies.postValue(Resource.Success(response.body()!!.result))
                } else {
                    _currencies.postValue(Resource.Error("API Error: ${response.message()}"))
                }
            } catch (e: Exception) {
                _currencies.postValue(Resource.Error("Network Error: ${e.localizedMessage}"))
            }
        }
    }
}

class CurrencyViewModelFactory(private val repository: CurrencyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrencyViewModel(repository) as T
    }
}
