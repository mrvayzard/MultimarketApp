package com.vayzard.core.ui.epoxy

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.vayzard.core.ui.R
import com.vayzard.core.ui.databinding.ItemMarginBinding
import com.vayzard.utils.extension.setMargins

@EpoxyModelClass
abstract class MarginEpoxyModel : ViewBindingEpoxyModelWithHolder<ItemMarginBinding>() {
  @EpoxyAttribute
  var horizontal: Int = 0

  @EpoxyAttribute
  var vertical: Int = 0

  override fun getDefaultLayout() = R.layout.item_margin

  override fun ItemMarginBinding.bind() {
    root.setMargins(start = horizontal, top = vertical)
  }
}
