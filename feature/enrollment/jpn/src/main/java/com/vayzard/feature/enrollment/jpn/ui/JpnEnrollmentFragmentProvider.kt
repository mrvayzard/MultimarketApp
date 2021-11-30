package com.vayzard.feature.enrollment.jpn.ui

import androidx.fragment.app.Fragment
import com.vayzard.feature.enrollment.ui.EnrollmentFragment
import com.vayzard.feature.enrollment.ui.EnrollmentFragmentProvider

class JpnEnrollmentFragmentProvider : EnrollmentFragmentProvider {
  override fun provide(): Fragment {
    return EnrollmentFragment.newInstance()
  }
}