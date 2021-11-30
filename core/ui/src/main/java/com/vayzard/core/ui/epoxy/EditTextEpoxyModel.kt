package com.vayzard.core.ui.epoxy

import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.vayzard.core.ui.R
import com.vayzard.core.ui.databinding.ItemEditTextBinding
import com.vayzard.utils.EMPTY_STRING
import com.vayzard.utils.extension.setTextIfDifferent

typealias OnTextChange = (String) -> Unit

@EpoxyModelClass
abstract class EditTextEpoxyModel : ViewBindingEpoxyModelWithHolder<ItemEditTextBinding>() {
  @EpoxyAttribute
  var value: String = EMPTY_STRING

  @EpoxyAttribute
  var hintMessage: String = EMPTY_STRING

  @EpoxyAttribute
  var errorMessage: String? = null

  @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
  var onTextChange: OnTextChange? = null

  override fun getDefaultLayout(): Int = R.layout.item_edit_text

  override fun ItemEditTextBinding.bind() {
    editText.apply {
      initTextWatcher()
      showErrorMessage()
      setupHint()
      setupValue()
    }
  }

  private fun EditText.setupValue() {
    setTextIfDifferent(value)
  }

  private fun EditText.setupHint() {
    hint = hintMessage
  }

  private fun EditText.initTextWatcher() {
    doAfterTextChanged {
      onTextChange?.invoke(it.toString())
    }
  }

  private fun EditText.showErrorMessage() {
    error = errorMessage
  }
}
