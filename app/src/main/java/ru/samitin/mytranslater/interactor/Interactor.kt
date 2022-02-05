package ru.samitin.mytranslater.interactor



interface Interactor<T> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}