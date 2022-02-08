package ru.samitin.mytranslater.model.datasource

import ru.samitin.mytranslater.model.data.AppState

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)
}