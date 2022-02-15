package ru.samitin.mytranslater.viewModel



interface Interactor<T> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}