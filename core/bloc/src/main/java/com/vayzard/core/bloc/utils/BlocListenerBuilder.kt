package com.vayzard.core.bloc.utils

import com.vayzard.core.bloc.Bloc
import com.vayzard.core.bloc.BlocAction
import com.vayzard.core.bloc.BlocListener
import com.vayzard.core.bloc.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <Action : BlocAction<State>, State, Event> buildBlocListener(
    block: (Transaction<Action, State>) -> Event?,
): BlocListener<Action, State, Event> {
  return object : BlocListener<Action, State, Event> {
    private val liveEvent = GuaranteedLiveEvent<Event>()
    override val singleEvents: Flow<Event>
      get() = liveEvent.events

    override suspend fun attach(bloc: Bloc<Action, State>) {
      bloc.transactionFlow.collect { transaction ->
        val event = block.invoke(transaction)
        if (event != null) {
          liveEvent.postEvent(event)
        }
      }
    }
  }
}

fun <Action : BlocAction<State>, State, Event> buildBlocListenerAndAttach(
    block: (Transaction<Action, State>) -> Event?,
    bloc: Bloc<Action, State>,
    scope: CoroutineScope,
): BlocListener<Action, State, Event> {
  return buildBlocListener(block).apply {
    scope.launch {
      attach(bloc)
    }
  }
}
