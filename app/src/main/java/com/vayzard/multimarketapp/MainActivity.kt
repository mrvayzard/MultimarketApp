package com.vayzard.multimarketapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vayzard.feature.enrollment.mxa.ui.EnrollmentFragmentMxa
import com.vayzard.feature.enrollment.ui.EnrollmentFragment

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    supportFragmentManager.beginTransaction()
      .add(R.id.fragmentContainerView, HomeFragment())
      .commit()
  }
}