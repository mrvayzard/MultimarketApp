package com.vayzard.feature.enrollment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vayzard.feature.enrollment.domain.EnrollmentBloc
import com.vayzard.feature.enrollment.domain.EnrollmentProcessor
import com.vayzard.feature.enrollment.domain.model.EnrollmentResult
import com.vayzard.feature.enrollment.ui.mapper.EnrollmentPresenter
import com.vayzard.feature.enrollment.ui.model.EnrollmentUiModel
import com.vayzard.feature.enrollment.ui.model.MessageState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
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
  private val enrollmentProcessor: EnrollmentProcessor,
) : ViewModel(), EnrollmentViewModelDelegate {
  override val stateFlow = enrollmentProcessor.enrollmentFlow()
    .map(enrollmentPresenter::toUiModel)

  init {
    viewModelScope.launch {
      enrollmentProcessor.enrollmentFlow().collect {
        processEnrollmentResult(it.result)
      }
    }
  }

  private val messageMutableStateFlow: MutableStateFlow<MessageState> = MutableStateFlow(MessageState.None)
  override val messageStateFlow: Flow<MessageState> = messageMutableStateFlow

  override fun showMessage(text: String) {
    messageMutableStateFlow.value = MessageState.TextMessage(text)
  }

  override fun onFirstNameChanged(value: String) {
    viewModelScope.launch {
      enrollmentProcessor.updateFirstName(value)
    }
  }

  override fun onLastNameChanged(value: String) {
    viewModelScope.launch {
      enrollmentProcessor.updateLastName(value)
    }
  }

  override fun onEnrollButtonClicked() {
    viewModelScope.launch {
      enrollmentProcessor.enroll()
    }
  }

  override fun onMessageShown() {
    messageMutableStateFlow.value = MessageState.None
  }

  private fun processEnrollmentResult(result: EnrollmentResult) {
    when (result) {
      is EnrollmentResult.Failure -> {
        showMessage(result.error.message ?: "Unknown error")
      }
      is EnrollmentResult.Success -> {
        showMessage(result.userInfo.toString())
      }
      EnrollmentResult.Idle -> {
        // do nothing
      }
    }
  }
}
