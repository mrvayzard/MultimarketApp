package com.vayzard.core.bloc

data class Transaction<Action, State>(
    val action: Action,
    val previousState: State?,
    val newState: State?,
)
