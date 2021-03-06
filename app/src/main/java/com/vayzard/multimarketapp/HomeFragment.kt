package com.vayzard.multimarketapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vayzard.feature.enrollment.ui.EnrollmentFragmentProvider
import com.vayzard.market.domain.Market
import com.vayzard.market.domain.MarketRepository
import com.vayzard.multimarketapp.databinding.FragmentHomeBinding
import com.vayzard.multimarketapp.di.MarketKoinModulesResolver
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class HomeFragment : Fragment(R.layout.fragment_home) {
  private val binding by viewBinding(FragmentHomeBinding::bind)

  private val marketRepository: MarketRepository by inject()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    fetchMarket()
    setupMarketSpinner()
    setupEnrollButton()
  }

  private fun setupEnrollButton() {
    binding.startEnrollmentButton.setOnClickListener {
      val fragmentProvider = get<EnrollmentFragmentProvider>()
      val fragment = fragmentProvider.provide()
      parentFragmentManager.beginTransaction()
        .addToBackStack(HomeFragment::class.simpleName)
        .replace(R.id.fragmentContainerView, fragment)
        .commit()
    }
  }

  private fun setupMarketSpinner() {
    binding.marketSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val market = when (position) {
          0 -> Market.US
          1 -> Market.Japan
          2 -> Market.Mexico
          else -> throw IllegalStateException("Unknown market selected")
        }
        saveSelectedMarket(market)
        changeEnvironment(market)
      }

      override fun onNothingSelected(p0: AdapterView<*>?) {
        // do nothing
      }
    }
  }

  private fun changeEnvironment(market: Market) {
    viewLifecycleOwner.lifecycleScope.launch {
      // remove old dependencies
      val currentMarket = marketRepository.getCurrentMarket()
      val currentMarketModules = MarketKoinModulesResolver.resolve(currentMarket)
      unloadKoinModules(currentMarketModules)

      // and apply new
      val selectedMarketModules = MarketKoinModulesResolver.resolve(market)
      loadKoinModules(selectedMarketModules)
    }
  }

  private fun saveSelectedMarket(market: Market) {
    viewLifecycleOwner.lifecycleScope.launch {
      marketRepository.selectMarket(market)
    }
  }

  private fun fetchMarket() {
    viewLifecycleOwner.lifecycleScope.launch {
      val id = when (marketRepository.getCurrentMarket()) {
        Market.US -> 0
        Market.Japan -> 1
        Market.Mexico -> 2
      }
      binding.marketSpinner.setSelection(id)
    }
  }
}