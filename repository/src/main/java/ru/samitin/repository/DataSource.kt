package ru.samitin.repository

interface DataSource<T> {

    suspend fun getData(word: String): T
}
