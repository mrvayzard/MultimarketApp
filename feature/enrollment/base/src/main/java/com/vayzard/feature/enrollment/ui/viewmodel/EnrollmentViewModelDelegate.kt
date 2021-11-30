package com.vayzard.feature.enrollment.ui.viewmodel

import com.vayzard.feature.enrollment.domain.model.EnrollmentState
import com.vayzard.feature.enrollment.domain.model.UserInfo
import com.vayzard.feature.enrollment.ui.MessageRenderer
import com.vayzard.feature.enrollment.ui.model.MessageState
import kotlinx.coroutines.flow.Flow

interface EnrollmentViewModelDelegate : MessageRenderer.Callback {
  val messageStateFlow: Flow<MessageState>

  suspend fun init(flow: Flow<EnrollmentState>)

  fun showError(exception: Exception)
  fun showUserInfo(userInfo: UserInfo)
  fun showMessage(text: String)
}

