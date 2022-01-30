package ru.samitin.mytranslater.view.base


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import ru.samitin.mytranslater.model.data.AppState
import ru.samitin.mytranslater.rx.SchedulerProvider

abstract class BaseViewModel<T: AppState>(
    protected open val _mutableLiveData:MutableLiveData<T> =MutableLiveData(),
) : ViewModel(){

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }
    protected fun cancelJob(){
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }
    abstract fun getData(word:String,isOnline:Boolean)//:LiveData<T> =liveDataForViewToObserve

    abstract fun handleError(error: Throwable)
}