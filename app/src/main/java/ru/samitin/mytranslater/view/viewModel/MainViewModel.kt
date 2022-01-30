package ru.samitin.mytranslater.view.viewModel

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.samitin.mytranslater.model.data.AppState
import ru.samitin.mytranslater.interactor.MainInteractor
import ru.samitin.mytranslater.utils.parseSearchResults
import ru.samitin.mytranslater.view.base.BaseViewModel

class MainViewModel(private val interactor: MainInteractor) :
    BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        // Запускаем корутину для асинхронного доступа к серверу с помощью
        // launch
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }
    private suspend fun startInteractor(word: String, isOnline: Boolean) = withContext(Dispatchers.IO) {
        _mutableLiveData.postValue(parseSearchResults(interactor.getData(word, isOnline)))
    }
    // Обрабатываем ошибки
    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }
}