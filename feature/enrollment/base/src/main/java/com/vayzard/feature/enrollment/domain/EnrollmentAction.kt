package com.vayzard.feature.enrollment.domain

import com.vayzard.core.bloc.BlocAction
import com.vayzard.feature.enrollment.domain.model.EnrollmentState
import com.vayzard.feature.enrollment.domain.validator.FirstNameValidator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

abstract class EnrollmentAction : BlocAction<EnrollmentState>
