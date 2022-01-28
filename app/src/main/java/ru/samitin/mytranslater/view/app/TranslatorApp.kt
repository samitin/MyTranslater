package ru.samitin.mytranslater.view.app

import android.app.Application

import org.koin.core.context.startKoin
import ru.samitin.mytranslater.di.koin.application
import ru.samitin.mytranslater.di.koin.mainScreen


class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(application, mainScreen))
        }
    }
}