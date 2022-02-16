package ru.samitin.utils.ui

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import java.lang.IllegalStateException
import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

class ViewByDelegate<out T : View>(private val rootGetter: () -> View?,
private val viewId: Int) {

    private var rootRef: WeakReference<View>? = null
    private var viewRef: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        var view = viewRef
        val cacheRoot = rootRef?.get()
        val currntRoot = rootGetter()
        if (currntRoot != cacheRoot || view == null) {
            if (currntRoot == null) {
                if (view != null) {
                    return view
                }
                throw IllegalStateException("Cannot get View, there is no root yet")
            }
            view = currntRoot.findViewById(viewId)
            viewRef = view
            rootRef = WeakReference(currntRoot)
        }
        checkNotNull(view) { "View with id \"$viewId\" not found in root" }
        return view
    }
}
    fun <T : View> Activity.viewById(@IdRes viewId: Int):ViewByDelegate<T>{
        return ViewByDelegate({window.decorView.findViewById(android.R.id.content)},viewId)
    }

    fun <T : View> Fragment.viewById(@IdRes viewId: Int): ViewByDelegate<T>{
        return ViewByDelegate({view},viewId)
    }
