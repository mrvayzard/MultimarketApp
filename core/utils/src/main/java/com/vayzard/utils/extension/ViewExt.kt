package com.vayzard.utils.extension

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.*

fun View.hideKeyboard() {
  val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
  val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.setMargins(
  start: Int = this.marginStart,
  top: Int = this.marginTop,
  end: Int = this.marginEnd,
  bottom: Int = this.marginBottom,
) {
  if (this.layoutParams is ViewGroup.MarginLayoutParams) {
    val p = this.layoutParams as ViewGroup.MarginLayoutParams
    p.updateMarginsRelative(start, top, end, bottom)
    this.requestLayout()
  }
}
