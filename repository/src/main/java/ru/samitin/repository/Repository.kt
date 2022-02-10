package ru.samitin.repository

interface Repository<T> {

    suspend fun getData(word: String): T
}
