package com.vayzard.feature.enrollment.mxa.domain

import com.vayzard.core.bloc.Bloc
import com.vayzard.feature.enrollment.domain.EnrollmentProcessor
import com.vayzard.feature.enrollment.domain.action.UpdateFirstNameReducer
import com.vayzard.feature.enrollment.domain.action.UpdateLastNameReducer
import com.vayzard.feature.enrollment.domain.model.EnrollmentState
import com.vayzard.feature.enrollment.mxa.domain.action.*
import com.vayzard.feature.enrollment.mxa.domain.model.EnrollmentSpecificState
import com.vayzard.feature.enrollment.mxa.domain.model.EnrollmentStateMxa
import com.vayzard.utils.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EnrollmentBlocMxa(
  private val enrollReducer: EnrollReducerMxa,
  private val updateFirstNameReducer: UpdateFirstNameReducer,
  private val updateLastNameReducer: UpdateLastNameReducer,
  private val updateMexicoSpecificFieldReducer: UpdateMexicoSpecificFieldReducerMxa,
  scope: CoroutineScope,
  dispatcherProvider: CoroutineDispatcherProvider
) : Bloc<EnrollmentActionMxa, EnrollmentStateMxa>(
  scope = scope,
  coroutineDispatcher = dispatcherProvider.default()
), EnrollmentProcessor {
  override fun initState(): EnrollmentStateMxa = EnrollmentStateMxa(
    EnrollmentState(),
    EnrollmentSpecificState()
  )

  override fun enrollmentFlow(): Flow<EnrollmentState> = asFlow().map { it.enrollmentBaseState }

  override fun enroll() {
    dispatch(EnrollActionMxa(enrollReducer))
  }

  override fun updateFirstName(value: String) {
    dispatch(UpdateFirstNameActionMxa(value, updateFirstNameReducer))
  }

  override fun updateLastName(value: String) {
    dispatch(UpdateLastNameActionMxa(value, updateLastNameReducer))
  }

  fun updateMexicoSpecificField(value: String) {
    dispatch(UpdateMexicoSpecificFieldActionMxa(value, updateMexicoSpecificFieldReducer))
  }
}
