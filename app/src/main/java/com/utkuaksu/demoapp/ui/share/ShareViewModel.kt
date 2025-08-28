package com.utkuaksu.demoapp.ui.share

import androidx.lifecycle.*
import com.utkuaksu.demoapp.data.model.currency.Currency
import com.utkuaksu.demoapp.data.model.share.Share
import com.utkuaksu.demoapp.data.repository.currency.CurrencyRepository
import com.utkuaksu.demoapp.data.repository.share.ShareRepository
import com.utkuaksu.demoapp.utils.Resource
import kotlinx.coroutines.launch

class ShareViewModel( private val repository: ShareRepository) : ViewModel() {

    private val _shares = MutableLiveData<Resource<List<Share>>>()
    init {

    }
    val shares: LiveData<Resource<List<Share>>> = _shares

    fun fetchShares() {
        viewModelScope.launch {
            _shares.postValue(Resource.Loading())
            try {
                val response = repository.getShares()
                if (response.isSuccessful && response.body() != null) {
                    _shares.postValue(Resource.Success(response.body()!!.result))
                } else {
                    _shares.postValue(Resource.Error("API Error: ${response.message()}"))
                }
            } catch (e: Exception) {
                _shares.postValue(Resource.Error("Network Error: ${e.localizedMessage}"))
            }
        }
    }
}

class ShareViewModelFactory(private val repository: ShareRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShareViewModel(repository) as T
    }
}
