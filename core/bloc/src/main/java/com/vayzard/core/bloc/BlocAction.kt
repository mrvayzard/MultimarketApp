package com.vayzard.core.bloc

import kotlinx.coroutines.flow.Flow

interface BlocAction<State> {
  suspend fun reduce(state: State?): Flow<State>
}