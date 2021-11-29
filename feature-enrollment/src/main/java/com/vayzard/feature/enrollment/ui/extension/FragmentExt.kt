package com.vayzard.feature.enrollment.ui.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.CoroutineScope

inline fun Fragment.launchAndRepeatWithViewLifecycle(
  minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
  crossinline block: suspend CoroutineScope.() -> Unit,
) {
  viewLifecycleOwner.launchAndRepeatWithViewLifecycle(minActiveState, block)
}
