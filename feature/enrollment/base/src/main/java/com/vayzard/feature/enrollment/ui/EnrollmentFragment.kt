package com.vayzard.feature.enrollment.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vayzard.feature.enrollment.R
import com.vayzard.feature.enrollment.databinding.FragmentEnrollmentBinding
import com.vayzard.feature.enrollment.ui.epoxy.EnrollmentEpoxyController
import com.vayzard.feature.enrollment.ui.epoxy.EnrollmentEpoxyProviderImpl
import com.vayzard.utils.extension.launchAndRepeatWithViewLifecycle
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class EnrollmentFragment : Fragment(R.layout.fragment_enrollment) {
  private val viewModel: EnrollmentViewModel by viewModel()
  private val binding by viewBinding(FragmentEnrollmentBinding::bind)

  private val controller by lazy {
    EnrollmentEpoxyController(
      callback = viewModel,
      enrollmentEpoxyProvider = EnrollmentEpoxyProviderImpl()
    )
  }
  private val messageRenderer by lazy {
    MessageRenderer(callback = viewModel)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupRecyclerView()
    observe()
  }

  private fun setupRecyclerView() {
    binding.epoxyRecyclerView.setController(controller)
  }

  private fun observe() {
    launchAndRepeatWithViewLifecycle {
      viewModel.stateFlow.collect { enrollmentUiModel ->
        controller.enrollmentUiModel = enrollmentUiModel
      }
    }
    launchAndRepeatWithViewLifecycle {
      viewModel.messageStateFlow.collect { message ->
        messageRenderer.showMessage(requireContext(), message)
      }
    }
  }

  companion object {
    fun newInstance(): EnrollmentFragment {
      return EnrollmentFragment()
    }
  }
}
