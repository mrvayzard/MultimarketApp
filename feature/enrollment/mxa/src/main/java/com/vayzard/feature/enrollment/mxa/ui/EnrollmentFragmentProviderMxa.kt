package com.vayzard.feature.enrollment.mxa.ui

import androidx.fragment.app.Fragment
import com.vayzard.feature.enrollment.ui.EnrollmentFragmentProvider

class EnrollmentFragmentProviderMxa : EnrollmentFragmentProvider {
  override fun provide(): Fragment {
    return EnrollmentFragmentMxa.newInstance()
  }
}