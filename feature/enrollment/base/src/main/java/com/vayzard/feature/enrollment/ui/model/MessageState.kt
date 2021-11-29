package com.vayzard.feature.enrollment.ui.model

sealed class MessageState {
  object None : MessageState()

  class TextMessage(
    val text: String
  ) : MessageState()
}