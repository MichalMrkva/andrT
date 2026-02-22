package com.company.uit.screens.mvi

import androidx.compose.runtime.Immutable

@Immutable
data class MviUIState(
	val inputField: String = "",
	val items: List<String> = emptyList()
)