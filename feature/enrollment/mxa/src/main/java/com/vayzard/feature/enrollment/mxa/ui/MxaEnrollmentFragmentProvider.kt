package com.vayzard.feature.enrollment.mxa.ui

import androidx.fragment.app.Fragment
import com.vayzard.feature.enrollment.ui.EnrollmentFragmentProvider

class MxaEnrollmentFragmentProvider : EnrollmentFragmentProvider {
  override fun provide(): Fragment {
    return EnrollmentFragmentMxa.newInstance()
  }
}