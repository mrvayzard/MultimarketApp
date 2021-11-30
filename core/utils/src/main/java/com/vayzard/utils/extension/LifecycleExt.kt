package com.vayzard.utils.extension

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

inline fun LifecycleOwner.launchAndRepeatWithViewLifecycle(
  minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
  crossinline block: suspend CoroutineScope.() -> Unit,
) {
  lifecycleScope.launch {
    lifecycle.repeatOnLifecycle(minActiveState) {
      block()
    }
  }
}