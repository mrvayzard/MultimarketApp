package com.vayzard.feature.enrollment.domain

import com.vayzard.core.bloc.Bloc
import com.vayzard.feature.enrollment.domain.action.*
import com.vayzard.feature.enrollment.domain.model.EnrollmentState
import com.vayzard.utils.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class EnrollmentBloc(
  private val updateFirstNameReducer: UpdateFirstNameReducer,
  private val updateLastNameReducer: UpdateLastNameReducer,
  private val enrollReducer: EnrollReducer,
  scope: CoroutineScope,
  dispatcherProvider: CoroutineDispatcherProvider
) : Bloc<EnrollmentAction, EnrollmentState>(
  scope = scope,
  coroutineDispatcher = dispatcherProvider.default()
), EnrollmentProcessor {
  override fun initState(): EnrollmentState = EnrollmentState()

  override fun enrollmentFlow(): Flow<EnrollmentState> = asFlow()

  override fun enroll() {
    dispatch(EnrollAction(enrollReducer))
  }

  override fun updateFirstName(value: String) {
    dispatch(UpdateFirstNameAction(value, updateFirstNameReducer))
  }

  override fun updateLastName(value: String) {
    dispatch(UpdateLastNameAction(value, updateLastNameReducer))
  }
}
