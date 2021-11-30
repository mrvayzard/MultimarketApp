package com.vayzard.feature.enrollment.mxa.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vayzard.feature.enrollment.mxa.R
import com.vayzard.feature.enrollment.mxa.databinding.FragmentEnrollmentMxaBinding
import com.vayzard.feature.enrollment.mxa.ui.model.EnrollmentUiModelMxa
import com.vayzard.feature.enrollment.ui.EnrollmentFragmentDelegate
import com.vayzard.feature.enrollment.ui.EnrollmentFragmentDelegateImpl
import com.vayzard.utils.extension.launchAndRepeatWithViewLifecycle
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class EnrollmentFragmentMxa : Fragment(R.layout.fragment_enrollment_mxa) {
  private val viewModel: EnrollmentViewModelMxa by viewModel()
  private val binding by viewBinding(FragmentEnrollmentMxaBinding::bind)

  private var delegate: EnrollmentFragmentDelegate? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // init base view delegate
    delegate = EnrollmentFragmentDelegateImpl(
      root = binding.root,
      firstNameEditText = binding.firstNameEditText,
      lastNameEditText = binding.lastNameEditText,
      enrollButton = binding.enrollButton,
      viewModel = viewModel
    ).also {
      lifecycle.addObserver(it)
    }

    // setup market specific view
    observe()
    setupView()
  }

  private fun observe() {
    launchAndRepeatWithViewLifecycle {
      viewModel.mexicoStateFlow.collect(::updateViewState)
    }
  }

  /**
   * Here we should carrie only about specific field, because other fields
   * are handling by [EnrollmentFragmentDelegate]
   */
  private fun updateViewState(state: EnrollmentUiModelMxa) {
    binding.mexicoSpecificFieldEditText.error = state.mexicoSpecificField.error?.message
  }

  /**
   * Here we should carrie only about MXA specific field, because other fields
   * are handling by [EnrollmentFragmentDelegate]
   */
  private fun setupView() {
    binding.mexicoSpecificFieldEditText.doAfterTextChanged {
      viewModel.onMexicoSpecificFieldChanged(it?.toString().orEmpty())
    }
  }

  companion object {
    fun newInstance(): EnrollmentFragmentMxa {
      return EnrollmentFragmentMxa()
    }
  }
}