package com.vayzard.feature.enrollment.us

import androidx.fragment.app.Fragment
import com.vayzard.feature.enrollment.ui.EnrollmentFragment
import com.vayzard.feature.enrollment.ui.EnrollmentFragmentProvider

class EnrollmentFragmentProviderUs : EnrollmentFragmentProvider {
  override fun provide(): Fragment {
    return EnrollmentFragment.newInstance()
  }
}