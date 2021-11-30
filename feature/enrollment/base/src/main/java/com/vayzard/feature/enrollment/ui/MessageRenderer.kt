package com.vayzard.feature.enrollment.ui

import android.content.Context
import android.widget.Toast
import com.vayzard.feature.enrollment.ui.model.MessageState

class MessageRenderer(
  private val callback: Callback
) {
  fun showMessage(context: Context, state: MessageState) {
    when (state) {
      is MessageState.TextMessage -> {
        Toast.makeText(context, state.text, Toast.LENGTH_SHORT).show()
        callback.onMessageShown()
      }
      MessageState.None -> {
        // do nothing
      }
    }
  }

  interface Callback {
    fun onMessageShown()
  }
}