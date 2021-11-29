package com.vayzard.feature.enrollment.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vayzard.feature.enrollment.di.FeatureEnrollmentScope
import com.vayzard.feature_enrollment.R
import com.vayzard.feature_enrollment.databinding.FragmentEnrollmentBinding
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel

class EnrollmentFragment : Fragment(R.layout.fragment_enrollment) {
  private val viewModel: EnrollmentViewModel by viewModel()
  private val binding by viewBinding(FragmentEnrollmentBinding::bind)

  private var delegate: EnrollmentFragmentDelegate? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    FeatureEnrollmentScope.getOrCreate(getKoin())
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    delegate = EnrollmentFragmentDelegateImpl(
      root = binding.root,
      firstNameEditText = binding.firstNameEditText,
      lastNameEditText = binding.lastNameEditText,
      enrollButton = binding.enrollButton,
      viewModel = viewModel
    ).also {
      lifecycle.addObserver(it)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    FeatureEnrollmentScope.close(getKoin())
  }
}
