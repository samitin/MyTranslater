package ru.samitin.mytranslater.view.base

import ru.samitin.mytranslater.model.data.AppState


interface View {

    fun renderData(appState: AppState)

}
