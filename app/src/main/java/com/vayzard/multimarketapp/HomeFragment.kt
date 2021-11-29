package com.vayzard.multimarketapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vayzard.feature.enrollment.mxa.ui.EnrollmentFragmentMxa
import com.vayzard.feature.enrollment.ui.EnrollmentFragment
import com.vayzard.multimarketapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
  private val binding by viewBinding(FragmentHomeBinding::bind)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.startEnrollmentButton.setOnClickListener {
      val fragment = if (binding.enableMxaMarketCheckBox.isChecked) {
        EnrollmentFragmentMxa()
      } else {
        EnrollmentFragment()
      }
      parentFragmentManager.beginTransaction()
        .addToBackStack(HomeFragment::class.simpleName)
        .replace(R.id.fragmentContainerView, fragment)
        .commit()
    }
  }
}