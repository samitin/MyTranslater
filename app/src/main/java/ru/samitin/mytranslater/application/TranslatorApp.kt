package ru.samitin.mytranslater.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.samitin.mytranslater.di.koin.application
import ru.samitin.mytranslater.di.koin.historyScreen
import ru.samitin.mytranslater.di.koin.mainScreen

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainScreen, historyScreen))
        }
    }
}