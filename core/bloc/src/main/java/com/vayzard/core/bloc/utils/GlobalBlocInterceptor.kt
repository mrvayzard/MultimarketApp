package com.vayzard.core.bloc.utils

import android.util.Log
import com.vayzard.core.bloc.BlocAction
import com.vayzard.core.bloc.Transaction

object GlobalBlocInterceptor {
  fun <Action : BlocAction<State>, State> onTransaction(transaction: Transaction<Action, State>) {
    System.out.println()
    Log.d("BlocInterceptor", "transaction = $transaction")
  }

  fun <Action> onAction(action: Action) {
    Log.d("BlocInterceptor", "action = $action")
  }
}
