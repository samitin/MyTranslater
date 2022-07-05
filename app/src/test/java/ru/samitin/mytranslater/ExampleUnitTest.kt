package ru.samitin.mytranslater

import org.junit.Test

import org.junit.Assert.*
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import ru.samitin.mytranslater.di.koin.mainScreen

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    class MyKoinTest : KoinTest {
        @Test
        fun test(){
            startKoin {listOf(mainScreen)}
            assertNotNull(mainScreen)
        }
    }
}