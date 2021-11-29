package com.vayzard.feature.enrollment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vayzard.feature.enrollment.domain.EnrollmentInteractor
import com.vayzard.feature.enrollment.ui.mapper.EnrollmentPresenter
import com.vayzard.feature.enrollment.ui.model.EnrollmentUiModel
import com.vayzard.feature.enrollment.ui.model.MessageState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Abstraction for fragment delegate
 */
interface EnrollmentViewModelDelegate {
  val stateFlow: Flow<EnrollmentUiModel>
  val messageStateFlow: Flow<MessageState>

  fun showMessage(text: String)

  fun onFirstNameChanged(value: String)
  fun onLastNameChanged(value: String)
  fun onEnrollButtonClicked()
  fun onMessageShown()
}

/**
 * Base view model implementation that contains shared logic of Enrollment screen.
 * It can be used as parent class for specific market implementation.
 */
open class EnrollmentViewModel(
  private val enrollmentPresenter: EnrollmentPresenter,
  private val enrollmentInteractor: EnrollmentInteractor,
) : ViewModel(), EnrollmentViewModelDelegate {
  override val stateFlow = enrollmentInteractor.enrollmentStateFlow
    .map(enrollmentPresenter::toUiModel)

  private val messageMutableStateFlow: MutableStateFlow<MessageState> = MutableStateFlow(MessageState.None)
  override val messageStateFlow: Flow<MessageState> = messageMutableStateFlow

  override fun showMessage(text: String) {
    messageMutableStateFlow.value = MessageState.TextMessage(text)
  }

  override fun onFirstNameChanged(value: String) {
    viewModelScope.launch {
      enrollmentInteractor.updateFirstName(value)
    }
  }

  override fun onLastNameChanged(value: String) {
    viewModelScope.launch {
      enrollmentInteractor.updateLastName(value)
    }
  }

  override fun onEnrollButtonClicked() {
    viewModelScope.launch {
      enrollmentInteractor.enroll()
        .onSuccess { userInfo -> showMessage(text = userInfo.toString()) }
        .onFailure { error -> showMessage(text = error.message ?: "Unknown error") }
    }
  }

  override fun onMessageShown() {
    messageMutableStateFlow.value = MessageState.None
  }
}
