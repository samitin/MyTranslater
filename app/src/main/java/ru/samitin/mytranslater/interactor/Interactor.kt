package ru.samitin.mytranslater.interactor

import io.reactivex.Observable

interface Interactor<T> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}