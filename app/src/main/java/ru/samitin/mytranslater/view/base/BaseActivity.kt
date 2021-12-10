package ru.samitin.mytranslater.view.base

import androidx.appcompat.app.AppCompatActivity
import ru.samitin.mytranslater.model.data.AppState


abstract class BaseActivity<T : AppState> : AppCompatActivity() {

  abstract val model:BaseViewModel<T>

    abstract fun renderData(appState: T)

}
