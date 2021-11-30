package com.vayzard.utils.extension

import android.content.res.Resources
import android.util.TypedValue

fun Float.toDP(resources: Resources): Float {
  return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, resources.displayMetrics)
}

fun Float.toPixels(resources: Resources): Float {
  return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, this, resources.displayMetrics)
}

val Float.dp
  get() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics
  ).toInt()

val Int.dp
  get() = toFloat().dp

fun Float?.orZero(): Float = this ?: 0f
