package com.vayzard.core.bloc.utils

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class GuaranteedLiveEvent<Event> {
  private val _events = Channel<Event>()
  val events = _events.receiveAsFlow()

  suspend fun postEvent(event: Event) {
    _events.send(event)
  }
}
