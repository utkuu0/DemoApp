package com.utkuaksu.demoapp.ui.emtia

import androidx.lifecycle.*
import com.utkuaksu.demoapp.data.model.emtia.Emtia
import com.utkuaksu.demoapp.data.repository.emtia.EmtiaRepository
import com.utkuaksu.demoapp.utils.Resource
import kotlinx.coroutines.launch

class EmtiaViewModel( private val repository: EmtiaRepository) : ViewModel() {

    private val _emtias = MutableLiveData<Resource<List<Emtia>>>()
    init {

    }
    val emtias: LiveData<Resource<List<Emtia>>> = _emtias

    fun fetchEmtias() {
        viewModelScope.launch {
            _emtias.postValue(Resource.Loading())
            try {
                val response = repository.getEmtias()
                if (response.isSuccessful && response.body() != null) {
                    _emtias.postValue(Resource.Success(response.body()!!.result))
                } else {
                    _emtias.postValue(Resource.Error("API Error: ${response.message()}"))
                }
            } catch (e: Exception) {
                _emtias.postValue(Resource.Error("Network Error: ${e.localizedMessage}"))
            }
        }
    }
}

class EmtiaViewModelFactory(private val repository: EmtiaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EmtiaViewModel(repository) as T
    }
}
