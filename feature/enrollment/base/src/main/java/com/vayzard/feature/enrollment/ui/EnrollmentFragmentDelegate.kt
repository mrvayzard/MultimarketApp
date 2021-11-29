package com.vayzard.feature.enrollment.ui

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.vayzard.feature.enrollment.ui.extension.launchAndRepeatWithViewLifecycle
import com.vayzard.feature.enrollment.ui.extension.setErrorIfDifferent
import com.vayzard.feature.enrollment.ui.extension.setTextIfDifferent
import com.vayzard.feature.enrollment.ui.model.EnrollmentUiModel
import com.vayzard.feature.enrollment.ui.model.MessageState
import kotlinx.coroutines.flow.collect

interface EnrollmentFragmentDelegate : LifecycleObserver {
  fun setupFirstNameEditText(editText: EditText)
  fun setupLastNameEditText(editText: EditText)
  fun setupEnrollButton(button: Button)

  fun updateViewState(state: EnrollmentUiModel)
  fun showMessage(state: MessageState)
}

/**
 * Base view implementation that contains shared logic of Enrollment screen.
 * It should be used for every specific market implementation.
 * This class is open, so you can override every method to change it's behaviour.
 */
open class EnrollmentFragmentDelegateImpl(
  private val root: View,
  private val firstNameEditText: EditText,
  private val lastNameEditText: EditText,
  private val enrollButton: Button,
  private val viewModel: EnrollmentViewModelDelegate
) : EnrollmentFragmentDelegate, DefaultLifecycleObserver {
  override fun onCreate(owner: LifecycleOwner) {
    super.onCreate(owner)
    observe(owner)
    setupView()
  }

  private fun observe(owner: LifecycleOwner) {
    owner.launchAndRepeatWithViewLifecycle {
      viewModel.stateFlow.collect(::updateViewState)
    }
    owner.launchAndRepeatWithViewLifecycle {
      viewModel.messageStateFlow.collect(::showMessage)
    }
  }

  private fun setupView() {
    setupFirstNameEditText(firstNameEditText)
    setupLastNameEditText(lastNameEditText)
    setupEnrollButton(enrollButton)
  }

  override fun setupFirstNameEditText(editText: EditText) {
    editText.doAfterTextChanged {
      viewModel.onFirstNameChanged(it?.toString().orEmpty())
    }
  }

  override fun setupLastNameEditText(editText: EditText) {
    editText.doAfterTextChanged {
      viewModel.onLastNameChanged(it?.toString().orEmpty())
    }
  }

  override fun setupEnrollButton(button: Button) {
    button.setOnClickListener {
      viewModel.onEnrollButtonClicked()
    }
  }

  override fun updateViewState(state: EnrollmentUiModel) {
    // update first name field
    firstNameEditText.setTextIfDifferent(state.firstName.value)
    firstNameEditText.setErrorIfDifferent(state.firstName.error?.message)
    // update last name field
    lastNameEditText.setTextIfDifferent(state.lastName.value)
    lastNameEditText.setErrorIfDifferent(state.lastName.error?.message)
  }

  override fun showMessage(state: MessageState) {
    when (state) {
      is MessageState.TextMessage -> {
        Toast.makeText(root.context, state.text, Toast.LENGTH_SHORT).show()
        viewModel.onMessageShown()
      }
      MessageState.None -> {
        // do nothing
      }
    }
  }
}