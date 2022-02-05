package ru.samitin.mytranslater.model.datasource



interface DataSource<T> {

    suspend fun getData(word: String): T
}
