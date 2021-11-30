package com.vayzard.core.ui.epoxy

import android.widget.Button
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.vayzard.core.ui.R
import com.vayzard.core.ui.databinding.ItemButtonBinding
import com.vayzard.utils.EMPTY_STRING
import com.vayzard.utils.extension.setTextIfDifferent

typealias OnButtonClicked = () -> Unit

@EpoxyModelClass
abstract class ButtonEpoxyModel : ViewBindingEpoxyModelWithHolder<ItemButtonBinding>() {
  @EpoxyAttribute
  var value: String = EMPTY_STRING

  @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
  var onClickListener: OnButtonClicked? = null

  override fun getDefaultLayout(): Int = R.layout.item_button

  override fun ItemButtonBinding.bind() {
    button.apply {
      setupText()
      initOnClickListener()
    }
  }

  private fun Button.setupText() {
    setTextIfDifferent(value)
  }

  private fun Button.initOnClickListener() {
    setOnClickListener {
      onClickListener?.invoke()
    }
  }
}
