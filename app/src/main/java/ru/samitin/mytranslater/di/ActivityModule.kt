package ru.samitin.mytranslater.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.samitin.mytranslater.view.main.MainActivity


@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}
