package ru.samitin.mytranslater.presenter

import ru.samitin.mytranslater.model.data.AppState
import ru.samitin.mytranslater.view.base.View

interface Presenter<T : AppState, V : View> {

    fun attachView(view: V)

    fun detachView(view: V)

    fun getData(word: String, isOnline: Boolean)
}
