package ru.samitin.mytranslater.model.repository



interface Repository<T> {

    suspend fun getData(word: String): T
}
