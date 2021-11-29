package com.vayzard.core.bloc

import kotlinx.coroutines.flow.Flow

interface BlocListener<Action : BlocAction<State>, State, SingleEvent> {
  val singleEvents: Flow<SingleEvent>
  suspend fun attach(bloc: Bloc<Action, State>)
}
