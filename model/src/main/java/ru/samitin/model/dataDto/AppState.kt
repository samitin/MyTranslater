package ru.samitin.mytranslater.model.data

import ru.samitin.model.data.DataModel
import ru.samitin.model.dataDto.SearchResultDto

sealed class AppState {

    data class Success(val data: List<DataModel>?) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}
