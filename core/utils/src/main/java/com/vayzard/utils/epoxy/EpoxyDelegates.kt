package com.vayzard.utils.epoxy

import com.airbnb.epoxy.EpoxyController
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> epoxyModelDelegate(initialValue: T, modelBuildDelay: Int = 0): ReadWriteProperty<Any?, T> =
  object : ReadWriteProperty<Any?, T> {

    private var delegateValue: T = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
      return delegateValue
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
      delegateValue = value
      when {
        thisRef is EpoxyController && modelBuildDelay > 0 -> thisRef.requestDelayedModelBuild(modelBuildDelay)
        thisRef is EpoxyController && modelBuildDelay == 0 -> thisRef.requestModelBuild()
      }
    }
  }
