package com.vayzard.core.bloc

import com.vayzard.core.bloc.utils.GlobalBlocInterceptor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.*

open class Bloc<Action : BlocAction<State>, State>(
  scope: CoroutineScope,
  coroutineDispatcher: CoroutineDispatcher,
) {
  companion object {
    private const val TRANSACTION_BUFFER_CAPACITY = 100
  }

  private val stateFlow = MutableStateFlow<State?>(null)

  private val _transactionFlow = MutableSharedFlow<Transaction<Action, State>>(
    extraBufferCapacity = TRANSACTION_BUFFER_CAPACITY,
    onBufferOverflow = BufferOverflow.DROP_OLDEST,
  )
  val transactionFlow: SharedFlow<Transaction<Action, State>> = _transactionFlow

  val currentState: State?
    get() = stateFlow.value

  private val actor = scope.actor<Action>(capacity = Channel.UNLIMITED) {
    initState()?.let { initialState -> stateFlow.value = initialState }
    channel.consumeEach { action ->
      onAction(action)
      flow {
        action.reduce(currentState).collect { state ->
          handleTransaction(action, state)
          emit(state)
        }
      }
        .flowOn(coroutineDispatcher)
        .collect { state ->
          stateFlow.value = state
        }
    }
  }

  private suspend fun handleTransaction(action: Action, state: State) {
    val transition = Transaction(action, currentState, state)
    _transactionFlow.emit(transition)
    onTransaction(transition)
  }

  protected open fun onAction(action: Action) {
    GlobalBlocInterceptor.onAction(action)
  }

  protected open fun onTransaction(transaction: Transaction<Action, State>) {
    GlobalBlocInterceptor.onTransaction(transaction)
  }

  fun asFlow(): Flow<State> = stateFlow.filterNotNull()

  protected open fun initState(): State? = null

  protected fun dispatch(action: Action) {
    actor.trySend(action)
  }
}
