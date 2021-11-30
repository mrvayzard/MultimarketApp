package com.vayzard.feature.enrollment.ui.viewmodel

import com.vayzard.feature.enrollment.domain.model.EnrollmentResult
import com.vayzard.feature.enrollment.domain.model.EnrollmentState
import com.vayzard.feature.enrollment.domain.model.UserInfo
import com.vayzard.feature.enrollment.domain.model.UserInfoDefault
import com.vayzard.feature.enrollment.ui.model.MessageState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect

open class EnrollmentViewModelDelegateDefault : EnrollmentViewModelDelegate {
  private val messageMutableStateFlow: MutableStateFlow<MessageState> = MutableStateFlow(MessageState.None)
  override val messageStateFlow: Flow<MessageState> = messageMutableStateFlow

  override suspend fun init(flow: Flow<EnrollmentState>) {
    flow.collect {
      processEnrollmentResult(it.result)
    }
  }

  override fun showMessage(text: String) {
    messageMutableStateFlow.value = MessageState.TextMessage(text)
  }

  override fun onMessageShown() {
    messageMutableStateFlow.value = MessageState.None
  }

  private fun processEnrollmentResult(result: EnrollmentResult) {
    when (result) {
      is EnrollmentResult.Failure -> showError(result.error)
      is EnrollmentResult.Success -> showUserInfo(result.userInfo)
      EnrollmentResult.Idle -> {
        // do nothing
      }
    }
  }

  override fun showError(exception: Exception) {
    val errorMessage = exception.message ?: "Unknown error"
    showMessage("Default implementation: $errorMessage")
  }

  override fun showUserInfo(userInfo: UserInfo) {
    if (userInfo is UserInfoDefault) {
      val message = StringBuilder()
        .appendLine("Default implementation:")
        .appendLine(userInfo.firstName)
        .appendLine(userInfo.lastName)
        .toString()
      showMessage(message)
    }
  }
}