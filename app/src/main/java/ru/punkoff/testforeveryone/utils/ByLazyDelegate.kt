package ru.punkoff.testforeveryone.utils

import androidx.lifecycle.ViewModel
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ByLazyDelegate<T : Any>(val block: () -> T?) : ReadWriteProperty<ViewModel, T?> {

    private var _value: T? = null
    private var isInitialized = false
    override fun getValue(thisRef: ViewModel, property: KProperty<*>): T? {
        if (!isInitialized) {
            isInitialized = true
            _value = block()
        }
        return _value
    }

    override fun setValue(thisRef: ViewModel, property: KProperty<*>, value: T?) {
        //   _value = value
    }
}