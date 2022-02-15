package ru.samitin.repository

import ru.samitin.mytranslater.model.data.AppState


interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(appState: AppState)
}
