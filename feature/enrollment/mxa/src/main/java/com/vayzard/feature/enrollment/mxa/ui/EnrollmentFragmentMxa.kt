package com.vayzard.feature.enrollment.mxa.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vayzard.feature.enrollment.mxa.R
import com.vayzard.feature.enrollment.mxa.databinding.FragmentEnrollmentMxaBinding
import com.vayzard.feature.enrollment.mxa.ui.epoxy.EnrollmentEpoxyControllerMxa
import com.vayzard.feature.enrollment.mxa.ui.epoxy.EnrollmentEpoxyProviderMxaImpl
import com.vayzard.feature.enrollment.mxa.ui.viewmodel.EnrollmentViewModelMxa
import com.vayzard.feature.enrollment.ui.MessageRenderer
import com.vayzard.feature.enrollment.ui.epoxy.EnrollmentEpoxyProviderImpl
import com.vayzard.utils.extension.launchAndRepeatWithViewLifecycle
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class EnrollmentFragmentMxa : Fragment(R.layout.fragment_enrollment_mxa) {
  private val viewModel: EnrollmentViewModelMxa by viewModel()
  private val binding by viewBinding(FragmentEnrollmentMxaBinding::bind)

  private val controller by lazy {
    EnrollmentEpoxyControllerMxa(
      callback = viewModel,
      enrollmentEpoxyProvider = EnrollmentEpoxyProviderImpl(),
      enrollmentEpoxyProviderMxa = EnrollmentEpoxyProviderMxaImpl()
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
      viewModel.stateFlow.collect { enrollmentUiModelMxa ->
        controller.enrollmentUiModelMxa = enrollmentUiModelMxa
      }
    }
    launchAndRepeatWithViewLifecycle {
      viewModel.messageStateFlow.collect { message ->
        messageRenderer.showMessage(requireContext(), message)
      }
    }
  }

  companion object {
    fun newInstance(): EnrollmentFragmentMxa {
      return EnrollmentFragmentMxa()
    }
  }
}