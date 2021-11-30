package com.vayzard.multimarketapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vayzard.feature.enrollment.di.featureEnrollmentModule
import com.vayzard.feature.enrollment.mxa.di.featureEnrollmentMxaModule
import com.vayzard.feature.enrollment.mxa.ui.EnrollmentFragmentMxa
import com.vayzard.feature.enrollment.ui.EnrollmentFragment
import com.vayzard.jpn.di.featureEnrollmentJpnModule
import com.vayzard.market.domain.Market
import com.vayzard.market.domain.MarketRepository
import com.vayzard.multimarketapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject

class HomeFragment : Fragment(R.layout.fragment_home) {
  private val binding by viewBinding(FragmentHomeBinding::bind)
  private val marketRepository: MarketRepository by inject()

  private var selectedMarket: Market = Market.Japan

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    fetchMarket()
    setupMarketSpinner()
    setupEnrollButton()
  }

  private fun setupEnrollButton() {
    binding.startEnrollmentButton.setOnClickListener {
      when (selectedMarket) {
        Market.US -> {
          getKoin().loadModules(listOf(featureEnrollmentModule))
          replaceFragment(EnrollmentFragment())
        }
        Market.Japan -> {
          getKoin().loadModules(listOf(featureEnrollmentJpnModule))
          replaceFragment(EnrollmentFragment())
        }
        Market.Mexico -> {
          getKoin().loadModules(listOf(featureEnrollmentMxaModule))
          replaceFragment(EnrollmentFragmentMxa())
        }
      }
    }
  }

  private fun replaceFragment(fragment: Fragment) {
    parentFragmentManager.beginTransaction()
      .addToBackStack(HomeFragment::class.simpleName)
      .replace(R.id.fragmentContainerView, fragment)
      .commit()
  }

  private fun setupMarketSpinner() {
    binding.marketSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val market = when (position) {
          0 -> Market.US
          1 -> Market.Japan
          3 -> Market.Mexico
          else -> Market.US
        }
        selectedMarket = market
        viewLifecycleOwner.lifecycleScope.launch {
          marketRepository.selectMarket(market)
        }
      }

      override fun onNothingSelected(p0: AdapterView<*>?) {

      }
    }
  }

  private fun fetchMarket() {
    viewLifecycleOwner.lifecycleScope.launch {
      val currentMarket = marketRepository.getCurrentMarket()
      selectedMarket = currentMarket
      val id = when (currentMarket) {
        Market.US -> 0
        Market.Japan -> 1
        Market.Mexico -> 2
      }
      binding.marketSpinner.setSelection(id)
    }
  }
}